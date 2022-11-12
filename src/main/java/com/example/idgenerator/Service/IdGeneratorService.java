package com.example.idgenerator.Service;

import com.example.idgenerator.Model.IdGenerator;
import com.example.idgenerator.Repository.IdGeneratorRepo;
import com.example.idgenerator.Utils.DateUtils;
import com.example.idgenerator.Utils.GenerateNodeId;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class IdGeneratorService {

    @Autowired
    IdGeneratorRepo idGeneratorRepo;

    private static int COUNTER;
    private static final int LIMIT = 2000;


    //Test
    private static final int EPOCH_BIT_LEN = 18;
    private static final int COUNTER_BITS = 2;

    @Autowired
    GenerateNodeId generateNodeId;


    public long getId() {
        long currentEpochMillSec = DateUtils.getEpochMills();
//        String currentEpochMillSecToBase10 = Long.toString(currentEpochMillSec, 10);
        String epochDateWithCounter = String.valueOf(currentEpochMillSec) + COUNTER;
        if (COUNTER % LIMIT == 0) {
            saveLastCounterUsed(currentEpochMillSec);
        }
        COUNTER++;
        return Long.parseLong(epochDateWithCounter);
    }


    public int getId2() {
        long currentEpochMillSec = DateUtils.getEpochMills();
        int nodeId = generateNodeId.generatingNodeId();
        int id = nodeId + COUNTER;

        if (COUNTER % LIMIT == 0) {
            saveLastCounterUsed(currentEpochMillSec);
        }
        COUNTER++;

        return id;
    }

    public void getCOUNTER() {
        COUNTER = idGeneratorRepo.getLastInsertedRecord()
                .map(IdGenerator::getLastUniqueId)
                .orElse(1) + LIMIT;

        System.out.println("COUNTER======" + COUNTER);
    }

    public void saveLastCounterUsed(long currentEpochMillSec) {
        long startTime = System.currentTimeMillis();
        long id = idGeneratorRepo.save(
                        IdGenerator
                                .builder()
                                .lastUniqueId(COUNTER)
                                .timeStamp(currentEpochMillSec)
                                .build())
                .getLastUniqueId();
        log.info("Saved last used counter {}, time taken {}", id, (System.currentTimeMillis() - startTime));
    }
}
