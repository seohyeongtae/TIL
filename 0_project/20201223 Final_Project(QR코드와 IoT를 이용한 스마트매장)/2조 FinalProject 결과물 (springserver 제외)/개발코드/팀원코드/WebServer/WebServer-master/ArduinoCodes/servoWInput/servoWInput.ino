#include <Servo.h>    // ì„œë³´ëª¨í„° ë¼ì´ë¸ŒëŸ¬ë¦¬
 
Servo servo;    // ì„œë³´ëª¨í„° ì‚¬ìš©ì„ ìœ„í•œ ê°ì²´ ìƒì„±
 
int motor = 7;  // ì„œë³´ëª¨í„°ì˜ í•€ 
int angle = 90; // ì„œë³´ëª¨í„° ì´ˆê¸° ê°ë„ ê°’
void setup() {
  servo.attach(motor);  // ì„œë³´ëª¨í„° ì—°ê²°
  Serial.begin(9600);  // ì‹œë¦¬ì–¼ ëª¨ë‹ˆí„° ì‹œìž‘
    
  Serial.println("Enter the u or d"); // u ë˜ëŠ” dí‚¤ ìž…ë ¥í•˜ê¸°
  Serial.println("u = angle + 15");   // uë¥¼ ëˆ„ë¥¸ë‹¤ë©´ í˜„ìž¬ ê°ë„ê°’ì—ì„œ +15ë„
  Serial.println("d = angle - 15\n");   // dë¥¼ ëˆ„ë¥¸ë‹¤ë©´ í˜„ìž¬ ê°ë„ê°’ì—ì„œ -15ë„
}
 
void loop() {
  if(Serial.available())  // ì‹œë¦¬ì–¼ëª¨ë‹ˆí„°ê°€ ì‚¬ìš©ê°€ëŠ¥í•  ë•Œ
  {
    char input = Serial.read(); // ë¬¸ìž ìž…ë ¥ë°›ê¸°
    
    if(input == 'u')    // u í‚¤ë¥¼ ëˆ„ë¥¼ ë•Œ
    {
      Serial.print("+15");  // '+15'ë¥¼ ì‹œë¦¬ì–¼ ëª¨ë‹ˆí„°ì— ì¶œë ¥
      for(int i = 0; i < 15; i++)  // í˜„ìž¬ ê°ë„ì—ì„œ 15ë„ ë”í•´ì£¼ê¸°
      {
        angle = angle + 1;   
        if(angle >= 180)
          angle = 180;
                    
        servo.write(angle); 
        delay(10);
      }
      Serial.print("\t\t");
      Serial.println(angle);  // í˜„ìž¬ ê°ë„ ì¶œë ¥
    } 
    else if(input == 'd')   // 'd'í‚¤ë¥¼ ìž…ë ¥í–ˆì„ ë•Œ
    {
      Serial.print("\t-15\t");  // '-15'ë¼ê³  ì¶œë ¥
      for(int i = 0 ; i < 15 ; i++)  // í˜„ìž¬ ê°ë„ì—ì„œ 15ë„ ë¹¼ì£¼ê¸°
      {
        angle = angle - 1;
        if(angle <= 0)
          angle = 0;
        servo.write(angle);
        delay(10);
      }
      Serial.println(angle);  // í˜„ìž¬ ê°ë„ ì¶œë ¥
    }
    else  // ìž˜ëª»ëœ ë¬¸ìžì—´ì„ ìž…ë ¥í–ˆì„ ë•Œ
    {
      Serial.println("wrong character!!");
    }
  }
}
 
