#!/usr/bin/env sh

export RUN_FIGURES=YES

# NOTE: Do not commit changes to the next line
export TEST_NAME=FINAL_RESULT


export LOG_FILE=logs/TEST_LOG_${TEST_NAME}.log
export LOG_FILTER_FILE=logs/TEST_RESULT_${TEST_NAME}.log
mkdir -p logs


./gradlew test -i --rerun-tasks > $LOG_FILE
cat $LOG_FILE | \
  grep -v "STANDARD_ERROR" | \
  grep -i "Program completed\|SmSimulatorTest" | \
  tee $LOG_FILTER_FILE
