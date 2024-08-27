MERGE INTO GRADING_QUEUE_NONFG_ASSIGNMENTS gqna
USING (
        SELECT :startDate start_date, :dueDate due_date, :assignmentId assignment_id, 
        :sectionId section_id
        from dual 
) dates
ON (gqna.section_id=dates.section_id and gqna.assignment_id=dates.assignment_id)
WHEN MATCHED THEN
    UPDATE SET START_DATE=TO_DATE(:startDate,'YYYY-MM-DD HH24:MI:SS'), DUE_DATE=TO_DATE(:dueDate,'YYYY-MM-DD HH24:MI:SS'),
    FORCE_GRADE_STATUS='NotStarted',UPDATED_DATE=sysdate
WHEN NOT MATCHED THEN
    INSERT (assignment_id,section_id,START_DATE, DUE_DATE,FORCE_GRADE_STATUS) 
    VALUES(:assignmentId,:sectionId,TO_DATE(:startDate,'YYYY-MM-DD HH24:MI:SS'),TO_DATE(:dueDate,'YYYY-MM-DD HH24:MI:SS'),'NotStarted')