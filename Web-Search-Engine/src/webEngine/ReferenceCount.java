package webEngine;

public class ReferenceCount implements Comparable {
	public String Reference;
	public int Count;
	
	ReferenceCount(String ref, int count){
		this.Reference = ref;
		this.Count = count;
	}
	
	public String getReferenceName() {
		return this.Reference; 
	}
	
	public void setReferenceCount(int count) {
		this.Count = count;
	}
	
	public int getCount() {
		return this.Count;
	}
	
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		ReferenceCount c = (ReferenceCount)o;
		return  c.Count - this.Count;
	}
}
