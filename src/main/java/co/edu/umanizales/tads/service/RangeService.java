package co.edu.umanizales.tads.service;

import co.edu.umanizales.tads.model.Ranges;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
public class RangeService {
    private List<Ranges> ranges;

    public RangeService(){
        ranges = new ArrayList<>();
        ranges.add(new Ranges(1,3));
        ranges.add(new Ranges(4,6));
        ranges.add(new Ranges(7,9));
        ranges.add(new Ranges(10,12));
        ranges.add(new Ranges(13,15));

    }
}