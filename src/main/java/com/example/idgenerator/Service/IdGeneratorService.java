package com.example.idgenerator.Service;

import com.example.idgenerator.Model.IdGenerator;
import com.example.idgenerator.Repository.IdGeneratorRepo;
import com.example.idgenerator.Utils.DateUtils;
import com.example.idgenerator.Utils.GenerateNodeId;
import com.example.idgenerator.Utils.GeneratePID;
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
    private static final int EPOCH_BIT_LEN = 41;
    private static final int PID = 13;
    private static final int COUNTER_BITS = 10;

    @Autowired
    GenerateNodeId generateNodeId;


    public synchronized long getId() {
        long currentEpochMillSec = DateUtils.getEpochMills();
        long id = 0L;

        id = currentEpochMillSec << (64 - EPOCH_BIT_LEN);
        int pid = GeneratePID.generatePid() % 2000;
        id |= pid << (64 - EPOCH_BIT_LEN - PID);

        if (COUNTER % LIMIT == 0) {
            saveLastCounterUsed(currentEpochMillSec);
        }
        int counter = COUNTER % 1024;
        id |= counter;

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
