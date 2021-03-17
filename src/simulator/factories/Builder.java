package simulator.factories;

import java.util.Collection;

import org.json.JSONObject;

public abstract class Builder <T> {
	protected String _type;
	protected String _desc;
	
	public Builder(String _type, String _desc){
		this._type = _type;
		this._desc = _desc;
	}
	
	public JSONObject getBuilderInfo() {
		JSONObject info = new JSONObject();
		info.put("type", _type);
		info.put("data", createData());
		info.put("desc", _desc);
		return info;
	}
	
	protected JSONObject createData() { return new JSONObject(); }

	public T createInstance(JSONObject info) {
		T b = null;
		if(_type != null && _type.equals(info.getString("type")))
			b = createTheInstance(info.getJSONObject("data"));
		return b;
	}
	
	protected abstract T createTheInstance(JSONObject data);

}