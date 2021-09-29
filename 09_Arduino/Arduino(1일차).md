## 아두이노 1일차

> 온라인교재 참고하기

Arduino 툴 -> 보드 Leonardo로 바꾸기(Latte Panda에 맞춰서)

포트는 Com3



void setup() 컨스트럭터 역할 초기화



void loop(){} 함수가 실행되는 부분

아두이노는 함수가 끝났다고해도 프로세스가 종료되는 것이 아니라 계속 실행이 된다.

IoT 장비는 한번 켜지면 계속 실행되기 때문에 if  문등으로 통제 해야 한다.



```arduino
String data;

void setup() {
  Serial.begin(9600);
  data = 1;

}

void loop() {
  delay(2000);
  Serial.println("Result: "+data);
  data = data+1;
}
```



```arduino
void setup() {
  Serial.begin(9600);

}

void loop() {

  delay(1000);
  if(Serial.available() > 0){
    String cmd = "";
    cmd = Serial.readString();
    cmd.trim();
    if(cmd =="s"){
      Serial.println("Start");
    }else{
      Serial.println("Stop");
    }
  }
}
```



Serial.available