SELECT p.id AS policy_id,
  p.exchane_key,
  NVL(assignment_policy.value,default_policies.policy_value)policy_value,
  p.is_content_policy,
  default_policies.name,
  default_policies.category_id,
  default_policies.category_level,
  default_policies.course_id,
  default_policies.instructor_id
FROM
  (SELECT NVL(category_policies.policy_id,system_policies.policy_id) policy_id,
    NVL(category_policies.policy_value,system_policies.policy_value)policy_value,
    NVL(category_policies.name,system_policies.name) name,
    NVL(category_policies.category_id,system_policies.category_id) category_id,
    NVL(category_policies.category_level,system_policies.category_level) category_level,
    NVL(category_policies.course_id,system_policies.course_id) course_id,
    NVL(category_policies.instructor_id,system_policies.instructor_id) instructor_id
  FROM
    (SELECT cpx.policy_id,
      cpx.policy_value,
      cat.name,
      cat.id AS category_id,
      cat.category_level,
      cat.course_id,
      cat.instructor_id
    FROM section sec,
      course c,
      brand b,
      brand_category_xref bcx,
      category cat,
      assignment assg,
      category_policy_xref cpx
    WHERE assg.assignment_id=:assignmentId
    AND sec.section_id      =:sectionId
    AND c.course_id         =sec.course_id
    AND c.discipline_id     =b.discipline_id
    AND b.brand_id          =bcx.brand_id
    AND cat.id              =bcx.category_id
    AND cat.category_level  ='system'
    AND cat.course_id      IS NULL
    AND upper(cat.name)     =upper(assg.category_type)
    AND cpx.category_id     =cat.id
    ) system_policies,
    (SELECT cpx.policy_id,
      cpx.policy_value,
      cat.name,
      cat.id AS category_id,
      cat.category_level,
      cat.course_id,
      cat.instructor_id
    FROM assignment assg,
      category_policy_xref cpx,
      category cat
    WHERE assg.assignment_id=:assignmentId
    AND assg.category_id    =cpx.category_id
    AND cpx.category_id     = cat.id
    ) category_policies
  WHERE system_policies.policy_id=category_policies.policy_id(+)
  ) default_policies,
  (SELECT apx.policy_id,
    apx.value
  FROM assignment_policy_xref apx
  WHERE assignment_id=:assignmentId
  AND section_id     =:sectionId
  ) assignment_policy,
  policy p
WHERE default_policies.policy_id=assignment_policy.policy_id(+)
AND p.id                        =default_policies.policy_id
AND p.deleted                   = 'N'
AND p.is_content_policy         ='N'