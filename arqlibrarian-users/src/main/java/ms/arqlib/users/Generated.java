package ms.arqlib.users;

import java.util.HashMap;
import java.util.Map;

public class Generated {

    private static final Map<String, Long> ids = new HashMap<>();
    public static long userId()
    {
        return idFor("user");
    }

    public static void resetUserId()
    {
        resetIdFor("user");
    }

    private static long idFor(String name)
    {
        if (ids.containsKey(name))
        {
            long id = ids.get(name);
            ids.put(name, increment(id));
        }
        else
        {
            long id = 1;
            ids.put(name, id);
        }

        return ids.get(name);
    }

    private static long increment(long id)
    {
        return id + 1;
    }

    private static void resetIdFor(String name)
    {
        ids.put(name, 0L);
    }
}
