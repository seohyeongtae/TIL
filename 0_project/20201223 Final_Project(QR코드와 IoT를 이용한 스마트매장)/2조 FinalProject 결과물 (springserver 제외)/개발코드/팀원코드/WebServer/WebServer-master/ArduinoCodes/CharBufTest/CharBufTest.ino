int i = 0;
char buf[100];
#define INPUT_SIZE 40

void setup() {
  // put your setup code here, to run once:

}

void loop() {
  char input[INPUT_SIZE + 1];

  if (Serial.available() > 0) {
    char result[100];
    char Test[50];
    strcpy(result, "Result-");
    byte size = Serial.readBytes(input, INPUT_SIZE); // size of input
    input[size] = 0;
    char* command = strtok(input, "&"); //split into substring

    while (command != 0) {
      // Split the command in two values
      char* separator = strchr(command, ':'); //search character
      if (separator != 0)
      {
        // Actually split the string in 2: replace ':' with 0
        *separator = 0;
        int sensorId = atoi(command); // String to int
        ++separator;
        int value = atoi(separator);

        // Do something with sensorId and value
        //1 = Door open, 2=Heating, 3=Cooling, 4=Lighting
        if (sensorId == 1) {
          strcat(result, ("Door:" + String(value)+"&").c_str());
        } else if (sensorId == 2) {
          strcat(result, ("Heating:" + String(value)+"&").c_str());
        } else if (sensorId == 3) {
          strcat(result, ("Cooling:" + String(value)+"&").c_str());
        } else if (sensorId == 4) {
          if (value == 1) {
            strcat(result, ("Lighting:" + String(value)+"&").c_str());
            //                   lightOn = true;
          } else if (value == 0) {
            //            lightOn = false;
          }
        }
      }
      // Find the next command in input string
      command = strtok(0, "&");
    }// while end
    result[strlen(result)-1] ='\0';
    Serial.println(result);
  }
}
