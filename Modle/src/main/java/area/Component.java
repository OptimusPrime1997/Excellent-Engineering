package area;

import service.Area;

public class Component implements Area{
	
	private String id;
	private String belongTo;
	
	
	public Component(String id, String belongTo) {
		super();
		this.id = id;
		this.belongTo = belongTo;
	}



	public String toXML() {
		// TODO Auto-generated method stub
		return null;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getBelongTo() {
		return belongTo;
	}


	public void setBelongTo(String belongTo) {
		this.belongTo = belongTo;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((belongTo == null) ? 0 : belongTo.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Component other = (Component) obj;
		if (belongTo == null) {
			if (other.belongTo != null)
				return false;
		} else if (!belongTo.equals(other.belongTo))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
}
