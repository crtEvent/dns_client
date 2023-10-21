# DNS Client
- 호스트명을 입력하면 입력한 호스트에 해당하는 IP 주소를 반환합니다. <br>(DNS 조회는 A타입으로만 조회한다.)

## 목차
- [실행 예](#실행-예시)
- [구조](#구조)
- [응답 메시지 분석](#응답-메시지-분석)
- [버그](#버그)

## 실행 예시
```text
호스트명을 입력하세요. (e.g. naver.com)
naver.com
223.130.195.95
223.130.200.107
223.130.200.104
223.130.195.200
```

## 구조
```text
+------------------+
|     InputView    |
+------------------+
          ⬇️
+------------------+
| RequestGenerator |
+------------------+
          ⬇️
+------------------+
|    DnsResolver   |
+------------------+
          ⬇️
+------------------+
|  ResponseParser  |
+------------------+
          ⬇️
+------------------+
|    OutputView    |
+------------------+
```
1. InputView
   1. 콘솔에서 호스트명을 입력받는다.
   2. 호스트명을 문자열로 반환한다.
2. RequestGenerator
   1. InputView가 반환한 호스트명 문자열을 입력받는다.
   2. 문자열을 . 기준으로 나누어 배열에 저장한다.
   3. 배열 안의 각 문자열을 byte로 변환한다.
   4. 변환된 byte 배열을 이용하여 byte 배열 형태의 요청 메시지를 만들어 반환한다.
3. DnsResolver
   1. RequestParser가 반환한 요청 메시지로 DNS 서버에 질의를 요청한다.
   2. 응답 메시지를 byte 배열 형태로 받아서 반환한다.
4. ResponseParser
   1. DnsResolver가 반환한 응답 메시지를 파싱하여 IP주소 목록을 ArrayList로 반환한다.
5. OutputView
   1. ResponseParser가 반환한 IP주소 목록을 콘솔에 출력한다.

## 응답 메시지 분석
- naver.com 을 입력한 경우
```text
received: 91 bytes
// Header Section
00 01 // ID
80 80 // FLAG
00 01 // QDCOUNT
00 04 // ANCOUNT
00 00 // NSCOUNT
00 00 // ARCOUNT

// Question Section
05 6e 61 76 65 72   // QNAME  : naver
03 63 6f 6d         // QNAME  : com
00                  // QNAME
00 01               // QTYPE  : A(1)
00 01               // QCLASS : IN(1) the Internet 

// Answer Section
c0 0c       // NAME     : 압축 포인터 (c0 0c = 1100 0000 0000 1100 = offset 12)
00 01       // TYPE     : A(1)
00 01       // CLASS    : IN(1) the Internet
00 00 00 b7 // TTL      : (b7 = 183)
00 04       // RDLENGTH : length in octets of the RDATA field.
df 82 c8 68 // RDATA    : 223.130.195.104

c0 0c       // NAME     : 압축 포인터 (c0 0c = 1100 0000 0000 1100 = offset 12)
00 01       // TYPE     : A(1)
00 01       // CLASS    : IN(1) the Internet
00 00 00 b7 // TTL      : (b7 = 183)
00 04       // RDLENGTH : length in octets of the RDATA field.
df 82 c8 6b // RDATA    : 223.130.195.107

c0 0c       // NAME     : 압축 포인터 (c0 0c = 1100 0000 0000 1100 = offset 12)
00 01       // TYPE     : A(1)
00 01       // CLASS    : IN(1) the Internet
00 00 00 b7 // TTL      : (b7 = 183)
00 04       // RDLENGTH : length in octets of the RDATA field.
df 82 c3 c8 // RDATA    : 223.130.195.20

c0 0c       // NAME     : 압축 포인터 (c0 0c = 1100 0000 0000 1100 = offset 12)
00 01       // TYPE     : A(1)
00 01       // CLASS    : IN(1) the Internet
00 00 00 b7 // TTL      : (b7 = 183)
00 04       // RDLENGTH : length in octets of the RDATA field.
df 82 c3 5f // RDATA    : 223.130.195.95
```
- google.com 으로 입력한 경우
```text
received: 44 bytes
// Header Section
00 01 // ID
80 80 // FLAG
00 01 // QDCOUNT
00 01 // ANCOUNT
00 00 // NSCOUNT
00 00 // ARCOUNT
 
// Question Section
06 67 6f 6f 67 6c 65 // QNAME : google
03 63 6f 6d          // QNAME : com
00                   // QNAME
00 01                // QTYPE  : A(1)
00 01                // QCLASS : IN(1) the Internet 

// Answer Section
c0 0c       // NAME     : 압축 포인터 (c0 0c = 1100 0000 0000 1100 = offset 12)
00 01       // TYPE     : A(1)
00 01       // CLASS    : IN(1) the Internet
00 00 01 20 // TTL      : (0000 0001 0010 0000 -> 이게 4개의 octet끼리 더해서 33인지, 4개의 octec을 통으로 봐서 288인지 모르겠다.)
00 04       // RDLENGTH : length in octets of the RDATA field.
8e fa ce ee // RDATA    : 142.250.196.142
```

## 버그
- 안되는 주소도 있음
```text
mathsisfun.com
onlinestringtools.com
```