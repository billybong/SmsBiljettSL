package se.billy.smsbiljett.sl.domain;

public class Ticket {

	private boolean zoneA = false;
	private boolean zoneB = false;
	private boolean zoneC = false;
	private boolean reducedPrice = false;
	
	public String getText(){
		return new StringBuffer()
		.append(reducedPrice?"R":"H")
		.append(zoneA?"A":"")
		.append(zoneB?"B":"")
		.append(zoneC?"C":"")
		.toString();
	}
	
	public int getPrice(){
		int nrOfZones = (zoneA?1:0) + (zoneB?1:0) + (zoneC?1:0);
		
		switch (nrOfZones)
		{
		case 1:
			return reducedPrice?20:36;
		case 2:
			return reducedPrice?30:54;
		case 3:
			return reducedPrice?40:72;
		default:
			return 0;
		}
	}
	
	public boolean isZoneA() {
		return zoneA;
	}
	public void setZoneA(boolean zoneA) {
		this.zoneA = zoneA;
	}
	public boolean isZoneB() {
		return zoneB;
	}
	public void setZoneB(boolean zoneB) {
		this.zoneB = zoneB;
	}
	public boolean isZoneC() {
		return zoneC;
	}
	public void setZoneC(boolean zoneC) {
		this.zoneC = zoneC;
	}
	public boolean isReducedPrice() {
		return reducedPrice;
	}
	public void setReducedPrice(boolean reducedPrice) {
		this.reducedPrice = reducedPrice;
	}
}
