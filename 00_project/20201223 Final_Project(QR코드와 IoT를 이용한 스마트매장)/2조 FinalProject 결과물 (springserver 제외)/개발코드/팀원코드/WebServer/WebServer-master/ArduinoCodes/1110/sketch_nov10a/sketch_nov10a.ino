int data;
void setup() {
  data = 1;
}

void loop() {
  Serial.println(data);
  data = data +1;
  delay(2000);
}
