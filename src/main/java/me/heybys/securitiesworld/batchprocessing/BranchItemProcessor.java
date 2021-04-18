package me.heybys.securitiesworld.batchprocessing;

import me.heybys.securitiesworld.entity.Branch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

public class BranchItemProcessor implements ItemProcessor<Branch, Branch> {

    private static final Logger log = LoggerFactory.getLogger(BranchItemProcessor.class);

    @Override
    public Branch process(final Branch branch) throws Exception {
        final String brCode = branch.getBrCode();
        final String brName = branch.getBrName();

        final Branch transformedBranch = new Branch(brCode, brName);

        return transformedBranch;
    }

}
