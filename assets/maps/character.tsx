<?xml version="1.0" encoding="UTF-8"?>
<tileset version="1.10" tiledversion="1.12.1" name="character" tilewidth="32" tileheight="32" tilecount="16" columns="4">
 <image source="../player/character.png" width="128" height="128"/>
 <tile id="0" type="GameObject">
  <properties>
   <property name="animation" value="IDLE"/>
   <property name="animationSpeed" type="float" value="2"/>
   <property name="atlasAsset" value="OBJECTS"/>
   <property name="cameraFollow" type="bool" value="true"/>
   <property name="controller" type="bool" value="true"/>
   <property name="speed" type="float" value="5"/>
  </properties>
  <objectgroup draworder="index" id="2">
   <object id="3" x="9.5" y="26.125" width="13.6875" height="4.875">
    <ellipse/>
   </object>
  </objectgroup>
 </tile>
 <tile id="1">
  <objectgroup draworder="index" id="2">
   <object id="1" x="9" y="2" width="16" height="30"/>
  </objectgroup>
 </tile>
 <tile id="2">
  <objectgroup draworder="index" id="2">
   <object id="1" x="8" y="1" width="17" height="30"/>
  </objectgroup>
 </tile>
 <tile id="3">
  <objectgroup draworder="index" id="2">
   <object id="1" x="8" y="2" width="16" height="30"/>
  </objectgroup>
 </tile>
 <tile id="4">
  <objectgroup draworder="index" id="2">
   <object id="1" x="7" y="2" width="17" height="30"/>
  </objectgroup>
 </tile>
 <tile id="5">
  <objectgroup draworder="index" id="2">
   <object id="1" x="7" y="3" width="17" height="29"/>
  </objectgroup>
 </tile>
 <tile id="6">
  <objectgroup draworder="index" id="2">
   <object id="1" x="7" y="2" width="17" height="30"/>
  </objectgroup>
 </tile>
 <tile id="7">
  <objectgroup draworder="index" id="2">
   <object id="1" x="7" y="3" width="17" height="29"/>
  </objectgroup>
 </tile>
 <tile id="8">
  <objectgroup draworder="index" id="2">
   <object id="1" x="8" y="2" width="17" height="30"/>
  </objectgroup>
 </tile>
 <tile id="9">
  <objectgroup draworder="index" id="2">
   <object id="1" x="8" y="3" width="17" height="29"/>
  </objectgroup>
 </tile>
 <tile id="10">
  <objectgroup draworder="index" id="2">
   <object id="1" x="8" y="2" width="17" height="30"/>
  </objectgroup>
 </tile>
 <tile id="11">
  <objectgroup draworder="index" id="2">
   <object id="1" x="8" y="3" width="17" height="29"/>
  </objectgroup>
 </tile>
 <tile id="12">
  <objectgroup draworder="index" id="2">
   <object id="1" x="8" y="1" width="17" height="31"/>
  </objectgroup>
 </tile>
 <tile id="13">
  <objectgroup draworder="index" id="2">
   <object id="1" x="9" y="2" width="16" height="30"/>
  </objectgroup>
 </tile>
 <tile id="14">
  <objectgroup draworder="index" id="2">
   <object id="1" x="8" y="1" width="17" height="31"/>
  </objectgroup>
 </tile>
 <tile id="15">
  <objectgroup draworder="index" id="2">
   <object id="1" x="8" y="2" width="16" height="30"/>
  </objectgroup>
 </tile>
</tileset>
