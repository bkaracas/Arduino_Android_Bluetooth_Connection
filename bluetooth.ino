#include <SoftwareSerial.h>
SoftwareSerial BTSerial(10,11);

char data;
void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  BTSerial.begin(38400);
  pinMode(5,OUTPUT);
} 

void loop() {
  if(BTSerial.available())
  Serial.write(BTSerial.read());
  data=BTSerial.read();
  
  if(Serial.available())
  BTSerial.write(Serial.read());

  if(data=='1'){
    digitalWrite(5,LOW);
  }
  if(data=='2'){
    digitalWrite(5,HIGH);
  }
}
