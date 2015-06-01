package org.bartelby.insideRouter;

public class TransientIdentifier {

	protected String parentName;
	protected Integer parentHash;
	
	public TransientIdentifier() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param parentName
	 */
	public TransientIdentifier(String parentName) {
		super();
		this.parentName = parentName;
	}

	/**
	 * @param parentName
	 * @param parentHash
	 */
	public TransientIdentifier(String parentName, Integer parentHash) {
		super();
		this.parentName = parentName;
		this.parentHash = parentHash;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Integer getParentHash() {
		return parentHash;
	}

	public void setParentHash(Integer parentHash) {
		this.parentHash = parentHash;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof TransientIdentifier){
			if(((TransientIdentifier) obj).getParentName().equals(this.getParentName()) && ((TransientIdentifier)obj).getParentHash() == this.getParentHash()){
				return true;
			}
		}
		
		return false;
	}

}
