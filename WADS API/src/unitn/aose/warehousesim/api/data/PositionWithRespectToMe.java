package unitn.aose.warehousesim.api.data;

import java.util.Collection;
import java.util.HashSet;

public class PositionWithRespectToMe {
	
	static public final PositionWithRespectToMe haed = new PositionWithRespectToMe(1,0);
	static public final PositionWithRespectToMe behind = new PositionWithRespectToMe(-1,0);
	static public final PositionWithRespectToMe right = new PositionWithRespectToMe(0,1);
	static public final PositionWithRespectToMe left = new PositionWithRespectToMe(0,-1);
	static public final PositionWithRespectToMe haedRight = new PositionWithRespectToMe(1,1);
	static public final PositionWithRespectToMe haedLeft = new PositionWithRespectToMe(1,-1);
	static public final PositionWithRespectToMe behindRight = new PositionWithRespectToMe(-1,1);
	static public final PositionWithRespectToMe behindLeft = new PositionWithRespectToMe(-1,-1);
	static public final PositionWithRespectToMe unknown = new PositionWithRespectToMe(null,null);

	static private Collection<PositionWithRespectToMe> collection = null;
	static public final Collection<PositionWithRespectToMe> values() {
		if(collection==null) {
			collection = new HashSet<PositionWithRespectToMe>();
			collection.add(haed);
			collection.add(behind);
			collection.add(right);
			collection.add(left);
			collection.add(haedRight);
			collection.add(haedLeft);
			collection.add(behindRight);
			collection.add(behindLeft);
			collection.add(unknown);
		}
		return collection;
	}
	
	private Integer forward;
	private Integer lateral;
	
	public PositionWithRespectToMe(Integer forward, Integer right) {
		this.forward = forward;
		this.lateral = right;
	}
	
	public Integer getForward() {
		return forward;
	}
	
	public Integer getLateral() {
		return lateral;
	}
	
	@Override
	public int hashCode() {
		if(null == forward || null == lateral) return -1;
		return (forward+1)|(lateral+1)<<2;
	};
	
	@Override
	public boolean equals(Object o){
		if(o instanceof PositionWithRespectToMe){
			PositionWithRespectToMe p = (PositionWithRespectToMe)o;
			return (forward==p.forward && lateral==p.lateral);	
		}else{
			return false;
		}
	}
	
	@Override
	public String toString(){
		return this.getClass().getSimpleName()+"[f:"+forward+";l:"+lateral+"]";
	}
}
