package database;

import java.util.List;
import java.util.Map;

public class GetParentName {

	
	private List<Map<String, String>> parentList;
	private List<Map<String, String>> grandParentList;
	
	private String parentCode;
	private String grandparentCode;
	
	public GetParentName(List<Map<String, String>> parentList,String parentCode){
		this.parentList = parentList;
		this.parentCode = parentCode;
	}
	
	public GetParentName(List<Map<String, String>> parentList,List<Map<String, String>> grandParentList,String parentCode){
		this.parentList = parentList;
		this.grandParentList = grandParentList;
		this.parentCode = parentCode;
	}
	
	public String getParentName(){
		String nameString = null;
		for (int i = 0; i < parentList.size(); i++) {
			if(parentList.get(i).get("code").equals(parentCode)){
				nameString =  parentList.get(i).get("name");
			}
		}
		return nameString;
	}
	
	public String getGrandparentName(){
		String nameString = null;
		for (int i = 0; i < parentList.size(); i++) {
			if(parentList.get(i).get("code").equals(parentCode)){
				grandparentCode = parentList.get(i).get("parent_code");
				break;
			}
		}
		for (int i = 0; i < grandParentList.size(); i++) {
			if(grandParentList.get(i).get("code").equals(grandparentCode)){
				nameString =  grandParentList.get(i).get("name");
			}
		}
		return nameString;
	}
	

}
