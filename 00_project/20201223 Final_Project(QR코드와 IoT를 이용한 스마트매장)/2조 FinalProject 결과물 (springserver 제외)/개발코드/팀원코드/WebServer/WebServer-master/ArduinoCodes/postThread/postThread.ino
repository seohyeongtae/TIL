#include <TimedAction.h>

 
//pin / state variables
#define ledPin 13
boolean ledState = false;
const int led1 = A0;
const int led2 = A1;
const int led3 = A2;

void led1Amp(){
   digitalWrite(led1, HIGH);
   delay(2);
   digitalWrite(led1,LOW);
   delay(10);
}

void led2Amp(){
   digitalWrite(led2, HIGH);
   delay(6);
   digitalWrite(led2,LOW);
   delay(10);   
}

void led3Amp(){
   digitalWrite(led3, HIGH);
   delay(10);
   digitalWrite(led3,LOW);
   delay(10);      
}

  TimedAction led1Thread = TimedAction(1000,led1Amp);
  TimedAction led2Thread = TimedAction(500,led2Amp);
  TimedAction led3Thread = TimedAction(11,led3Amp);

 
void setup(){
  Serial.begin(9600);
  pinMode(led1,OUTPUT);
  pinMode(led2,OUTPUT);
  pinMode(led3,OUTPUT);
  
}
 
void loop(){
  led1Thread.check();
  led2Thread.check();
  led3Thread.check();
  
}
 
