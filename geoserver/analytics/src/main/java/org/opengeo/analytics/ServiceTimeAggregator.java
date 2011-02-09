package org.opengeo.analytics;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.geoserver.monitor.RequestData;

/**
 * Aggregats request data grouped by service over time.
 * @author Justin Deoliveira, OpenGeo
 *
 */
public class ServiceTimeAggregator extends RequestDataAggregator {

    View view;
    Set<String> services;
    Map<String,TimeAggregator> data = new HashMap();
    
    public ServiceTimeAggregator(Date from, Date to, View view) {
        this(from, to, view, new HashSet());
    }
    
    public ServiceTimeAggregator(Date from, Date to, View view, Set<String> services) {
        super(from, to);
        this.view = view;
        this.services = services;
    }

    public void visit(RequestData req, Object... aggregates) {
        String service = req.getService();
        service = service != null ? service : Service.OTHER.name();
        
        if (!services.isEmpty() && !services.contains(service)) {
            return;
        }
        
        TimeAggregator del = data.get(service);
        if (del == null) {
            del = new TimeAggregator(from, to, view);
            data.put(service, del);
        }
        
        del.visit(req, aggregates);
    }
    
    public Map<String,long[]> getData() {
        Map<String,long[]> result = new HashMap();
        
        for (Map.Entry e : data.entrySet()) {
            result.put((String)e.getKey(), ((TimeAggregator)e.getValue()).getData());
        }
        
        return result;
    }
    
    public long[][] getRawData() {
        long[][] result = new long[data.size()][];
        int i = 0;
        for (Map.Entry e : data.entrySet()) {
            result[i++] = ((TimeAggregator)e.getValue()).getData();
        }
        return result;
    }

}