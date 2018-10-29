package research.wechat;

public abstract class TemplateData {
	
	    private Keynote first;

	    private Keynote remark;
	    
	    public TemplateData(){
	    	
	    }

		public Keynote getFirst() {
			return first;
		}

		public void setFirst(Keynote first) {
			this.first = first;
		}
		public Keynote getRemark() {
			return remark;
		}

		public void setRemark(Keynote remark) {
			this.remark = remark;
		}
	    
	    
}
