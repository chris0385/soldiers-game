<?xml version="1.0" encoding="UTF-8"?>
<tileset name="Tiles" tilewidth="32" tileheight="32" tilecount="25" columns="5">
 <image source="Tiles.png" width="160" height="160"/>
 <terraintypes>
  <terrain name="a" tile="4"/>
 </terraintypes>
 <tile id="1" type="ground"/>
 <tile id="2" type="water">
  <properties>
   <property name="class" value="water"/>
  </properties>
 </tile>
 <tile id="3" type="road"/>
 <tile id="4" type="rocks"/>
 <tile id="17">
  <properties>
   <property name="class" value="unit"/>
   <property name="type" value="light"/>
  </properties>
 </tile>
 <tile id="21">
  <properties>
   <property name="class" value="unit"/>
   <property name="type" value="laser"/>
  </properties>
 </tile>
 <tile id="23">
  <properties>
   <property name="class" value="flag"/>
  </properties>
 </tile>
</tileset>
