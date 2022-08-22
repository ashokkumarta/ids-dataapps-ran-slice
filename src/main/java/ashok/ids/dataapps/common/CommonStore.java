package ashok.ids.dataapps.common;

import java.util.HashMap;

public class CommonStore {
	
	private ThreadLocal<HashMap<String, String>> threadStore = new ThreadLocal<>();
	
	public void set(String name, String value) {
		if (null == threadStore.get()) {
			threadStore.set(new HashMap<String, String>());
		}
		threadStore.get().put(name, value);
	}
	
	public String get(String name) {
		if (null == threadStore.get()) {
			return CommonBase.EMPTY;
		}
		if (null == threadStore.get().get(name)) {
			return CommonBase.EMPTY;
		}
		return threadStore.get().get(name);
	}
	
	public void reset() {
		threadStore.remove();
	}

}
