#include "LedControl.h"


// DIN,CLK,CS,Number of Matrix
LedControl lc = LedControl(A8, A9, A10, 4);
int num;


void setup() {
  for (num = 0; num < 4; num++) // 留ㅽ듃由?뒪 0踰덈???3踰덇퉴吏 ?명똿
  {
    lc.shutdown(num, false);
    lc.setIntensity(num, 8);
    lc.clearDisplay(num);
  }
} // setup finished

// matrix ?⑥닔 ?좎뼵
void matrix() {
  // ?쒓? '留?瑜??댁쭊?섎줈 諛곗뿴 ?좎뼵
  //http://dotmatrixtool.com/#
  const byte x[8] =
  {
    0x00, 0x00, 0x3f, 0x20, 0x20, 0xa8, 0x70, 0x20
  }; // arrow head
  // ?쒓? '??瑜??댁쭊?섎줈 諛곗뿴 ?좎뼵
  byte r[8] = {
    0x00, 0x00, 0xff, 0x00, 0x00, 0x00, 0x00, 0x00
  }; //-


  // ?쒓? '由?瑜??댁쭊?섎줈 諛곗뿴 ?좎뼵
  byte t[8] = {
    0x00, 0x00, 0xff, 0x00, 0x00, 0x00, 0x00, 0x00
  }; // ------
  // ?쒓? '??瑜??댁쭊?섎줈 諛곗뿴 ?좎뼵
  byte m[8] = {
    0x00, 0x00, 0xff, 0x00, 0x00, 0x00, 0x00, 0x00
  }; // --------


  // lc.setRow ?⑥닔????Row) 湲곗??쇰줈 ?꾪듃留ㅽ듃由?뒪瑜??쒖뼱 lc.setRow(matrix_number,Row,value)
  for (int j = 7; j >= 0; j--) {
    lc.setRow(0, j, m[j]); // 0踰덉㎏ 留ㅽ듃由?뒪?먯꽌 '留?異쒕젰
    lc.setRow(1, j, t[j]); // 1踰덉㎏ 留ㅽ듃由?뒪?먯꽌 '??異쒕젰
    lc.setRow(2, j, r[j]); // 2踰덉㎏ 留ㅽ듃由?뒪?먯꽌 '由?異쒕젰
    lc.setRow(3, j, x[j]); // 3踰덉㎏ 留ㅽ듃由?뒪?먯꽌 '??異쒕젰
  }
  delay(1000);
} // matrix function finished


void loop() {
  matrix();
  for (num = 0; num < 4; num++)  {
    lc.clearDisplay(num);
  }
  delay(1000);
}
