const int LED_PIN = 10;

unsigned long remainder = 0;
const int OFF_TIME = 10;

void setup() {
  Serial.begin(9600);
  pinMode( LED_PIN, OUTPUT );
}
int input;
void loop() {
  unsigned long currentMillis = millis();
  if (Serial.available() > 0) {
    input = Serial.readString().toInt();
    Serial.println(input);
  }
  remainder = currentMillis % ( input + OFF_TIME);
  if (remainder < input) {
    digitalWrite( LED_PIN, HIGH );
  } else {
    digitalWrite( LED_PIN, LOW );
  }
}
