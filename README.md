This project is used for executing adb command from java project or any android project.

  Usage:
  ======
  ADBService adbService = new ADBServiceImpl();

  List<String> udids = adbService.getConnectedDevicesUdid();

  byte[] rawScreenshot = adbService.getScreenshot("deviceUdid", ADBScreenshotType.RAW);

  Map configuration = adbService.getPropertiesForDevice("deviceUdid");

  byte[] file = adbService.pullFile(deviceUdid, new ADBRemoteFile("/sdcard/testFile.txt"));

  adbService.pushFile(deviceUdid, new File("pom.xml"), new ADBRemoteFile("/sdcard/pom.xml"));

  adbService.sendKeys(deviceUdid, "testSendKeys via ADB");

  List<ADBProcess> list = adbService.getProcessList(deviceUdid);

  Adding procedure of this jar into different projects using maven(into pom file):
  ================================================================================

  <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
      ...................................

      <repositories>
          ...............................
          <repository>
              <id>adbhelper-mvn-repo</id>
              <url>https://raw.github.com/reversecoder/adbhelper/mvn-repo/</url>
              <snapshots>
                  <enabled>true</enabled>
                  <updatePolicy>always</updatePolicy>
              </snapshots>
          </repository>
          ...............................
      </repositories>

      <dependencies>
          ...............................
          <dependency>
              <groupId>com.reversecoder.adbhelper</groupId>
              <artifactId>adbhelper</artifactId>
              <version>1.0-SNAPSHOT</version>
          </dependency>
          ...............................
      </dependencies>

      ...................................
  </project>
