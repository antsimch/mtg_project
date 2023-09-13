package sg.edu.nus.iss.mtg_server.storage;

import java.util.HashMap;
import java.util.Map;

import sg.edu.nus.iss.mtg_server.models.Draft;

public class DraftStorage {
    
    private static Map<String, Draft> drafts;
    private static DraftStorage instance;

    private DraftStorage() {
        drafts = new HashMap<>();
    }

    public static synchronized DraftStorage getInstance() {
        if (instance == null) {
            instance = new DraftStorage();
        }
        return instance;
    }

    public Map<String, Draft> getDrafts() {
        return drafts;
    }

    public void setDraft(Draft draft) {
        drafts.put(draft.getDraftId(), draft);
    }
}
