String data;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  data = 1;
}

void loop() {
  // put your main code here, to run repeatedly:
  delay(2000);
  Serial.println("Result: " + data);
  data += 1;
}
