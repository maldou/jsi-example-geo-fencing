jsi-examples-geo-fencing extends jsi-examples
============

Simple examples of how to use the JSI library. The following commands will run the examples:

    git clone https://github.com/aled/jsi-examples.git
    cd jsi-examples
    mvn package
    cd target
    unzip jsi-examples-1.1.0-SNAPSHOT-jar-with-dependencies.jar
    java -cp .:./classes net.sf.jsi.examples.Contains
    java -cp .:./classes net.sf.jsi.examples.NearestN   
    java -cp .:./classes net.sf.jsi.examples.area.Areas
    
    use：
    Areas areas = Areas.getInstance();
	AreaEntity area1 = new AreaEntity();
	area1.setCity("天津市"); //城市
	area1.setName("南开大学"); //地理区域名称
	area1.setCoords("117.175726 39.106613,117.175739 39.106202,117.177346 39.106204,117.178402 39.106221"); //地理区域坐标
	areas.addAraea(area1);
	
	AreaEntity area = areas.inArea(117.17702,39.10606);
	if(area != null) {
		System.out.println(area.getName() + "," + area.getCity());
	}
