float temperature;  
int reading;  
int lm35Pin = A1;
const int ledPin = A2;

void setup()  
{
    analogReference(INTERNAL);
    Serial.begin(9600);
    pinMode(ledPin,OUTPUT);
}

void loop()  
{
    reading = analogRead(lm35Pin);
    temperature = reading / 9.31;
    if(temperature <= 13){
      digitalWrite(ledPin, HIGH);
      Serial.println("Power on");
    }else{
      digitalWrite(ledPin, LOW);
      Serial.println("Power off");
    }
    Serial.println(temperature);
    delay(1000);
}
