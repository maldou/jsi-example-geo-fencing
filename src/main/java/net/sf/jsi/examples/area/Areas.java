package net.sf.jsi.examples.area;

import gnu.trove.procedure.TIntProcedure;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import net.sf.jsi.Point;
import net.sf.jsi.Rectangle;
import net.sf.jsi.SpatialIndex;
import net.sf.jsi.rtree.RTree;

public class Areas {
	
	private AtomicInteger index = new AtomicInteger(0);
	private static Map<Integer, AreaEntity> areaMap = new ConcurrentHashMap<Integer, AreaEntity>();
	
	private SpatialIndex si;
	
	private Areas() {
		si = new RTree();
		si.init(null);
	}
	private static Areas instance = new Areas();
	public static Areas getInstance() {
		return instance;
	}
	
	public void addAraea(AreaEntity area) {
		int id = index.addAndGet(1);
		areaMap.put(id, area);
		Rectangle rect = area.mbr();
		si.add(rect, id);
	}
	
	public AreaEntity inArea(double x, double y) {
		AreaProcedure areaProc = new AreaProcedure();
		Point point = new Point((float)x, (float)y);
		si.nearest(point, areaProc, 0f);
		AreaEntity area = areaProc.getArea();
		if(area == null) {
			return null;
		}
		if(isPointIn(x, y, area)) {
			return area;
		}
		return null;
		
	}

	private boolean isPointIn(double x, double y, AreaEntity area) {
		return area.isPoinIn(x, y);
	}
	
	private static class AreaProcedure implements TIntProcedure {
		private AreaEntity area;
		
		public boolean execute(int id) {
			area = areaMap.get(id);
			return true;
		};

		public AreaEntity getArea() {
			return area;
		}
	};
	
	public static void main(String args[]) {
		Areas areas = Areas.getInstance();
		AreaEntity area1 = new AreaEntity();
		area1.setCity("武汉市");
		area1.setName("武汉天河国际机场");
		area1.setCoords("114.193164 30.763670,114.221158 30.789140,114.228346 30.782056,114.203575 30.755664,114.193164 30.763670");
		areas.addAraea(area1);
		AreaEntity area2 = new AreaEntity();
		area2.setCity("南京市");
		area2.setName("南京南站");
		area2.setCoords("118.799594 31.960875,118.797019 31.964939,118.796168 31.966063,118.794575 31.968888,118.794100 31.969679,118.793823 31.970750,118.794060 31.971671,118.794555 31.972444,118.795679 31.972904,118.796942 31.973147,118.797941 31.973143,118.798904 31.972543,118.799693 31.971842,118.800277 31.971308,118.801206 31.969637,118.802077 31.967947,118.806395 31.961265,118.803523 31.960174,118.800543 31.959599,118.799594 31.960875");
		areas.addAraea(area2);
		AreaEntity area3 = new AreaEntity();
		area3.setCity("天津市");
		area3.setName("南开大学");
		area3.setCoords("117.175726 39.106613;117.175739 39.106202;117.177346 39.106204;117.178402 39.106221;117.179014 39.106264;117.178971 39.10347;117.178952 39.10317;117.178891 39.102611;117.178865 39.10254;117.178761 39.102476;117.178671 39.102425;117.178533 39.102343;117.17841 39.102232;117.17827 39.102053;117.178218 39.102012;117.178142 39.101994;117.177113 39.101845;117.176294 39.101618;117.174919 39.101281;117.174324 39.101151;117.173522 39.101008;117.173185 39.100978;117.171501 39.100862;117.171178 39.10084;117.170466 39.100785;117.169587 39.100724;117.168378 39.100655;117.168265 39.100649;117.167651 39.100549;117.165807 39.10047;117.165376 39.100449;117.163162 39.100366;117.16105 39.100234;117.15846 39.100109;117.157911 39.100076;117.157934 39.100227;117.157969 39.100518;117.158025 39.100853;117.158253 39.102242;117.158467 39.102218;117.158628 39.102136;117.158644 39.102271;117.158751 39.103009;117.158794 39.103099;117.158918 39.103618;117.158918 39.103828;117.158828 39.103831;117.158547 39.103851;117.156675 39.103901;117.155709 39.103927;117.155767 39.105175;117.155822 39.106044;117.155887 39.106084;117.155979 39.106105;117.156094 39.106104;117.156503 39.106097;117.157033 39.106091;117.158883 39.106081;117.159219 39.106078;117.159771 39.106024;117.16064 39.105935;117.161021 39.105892;117.161788 39.105817;117.162171 39.105763;117.162423 39.105684;117.162554 39.105639;117.162612 39.105852;117.16264 39.105975;117.162696 39.106226;117.163391 39.106211;117.163618 39.10616;117.163903 39.106113;117.165471 39.106064;117.165904 39.106064;117.166227 39.10611;117.167631 39.106114;117.167636 39.10632;117.168732 39.106161;117.169728 39.106173;117.171836 39.106174;117.1748 39.106182;117.174807 39.106609;117.175726 39.106613".replaceAll(";", ","));
		areas.addAraea(area3);
		
		AreaEntity area = areas.inArea(117.16302,39.10106);
		if(area != null) {
			System.out.println(area.getName() + "," + area.getCity());
		}
		
	}
	
}
