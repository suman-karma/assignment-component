// **PLEASE DONT DO ANY SONAR FIXES FOR THIS CLASS**
/*
 * $Source:
 * /web/cvs/connect-suite/connect/connect-server/src/main/java/com/mhe/connect/business/gradebook/valueobj/Assignment
 * .java,v $ $Revision$ $Date$ Assignment.java Copyright 2001 The McGraw-Hill Companies.
 * All Rights Reserved Created on Jul 9, 2003, 12:50:46 PM by Kameshwar.
 */

package io.mhe.assignmentcomponent.vo;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import io.mhe.assignmentcomponent.common.util.DateUtil;
import io.mhe.assignmentcomponent.common.util.GenUtil;

/**
 * 
 * @author Kameshwar $Author$
 */
public class Assignment implements Model, Comparable, Cloneable {
	private String	validationView	= "";

	public String getValidationView() {
		return validationView;
	}

	public void setValidationView(String validationView) {
		this.validationView = validationView;
	}

	private static final Logger logger						= LoggerFactory.getLogger(Assignment.class);
	private String						url							= null;
	private String						isbn						= null;
	private String						assignmentReferenceId;

	private String						provider;
	private String						previewURL;

	private boolean						adaptiveStudyPlan			= false;

	protected Date						formattedStartDate			= new Date();
	protected Date						formattedDueDate			= new Date();

	private long						_id							= 0L;
	private String						_category					= "None";								// set
																											// the
																											// cetegory
																											// type
																											// to
																											// None
																											// by
	// default.
	//Added for Proctoring
	private boolean                      proctoringEnabled          = false;
	private String						_title						= "";
	protected Date						_startDate					= new Date();

	protected Date						_startDateByCourseTZ		= new Date();
	protected Date						_dueDate					= new Date();

	protected Date						_dueDateByCourseTZ			= new Date();
	private Date						_delayedFeedbackDate		= new Date();
	private float						_wieght						= 0F;
	private String						_type								= "FLAGGED";
	private long						_duration					= 0L;
	//private DisplayType					_displayType				= DisplayType.WORST;
	private String						_note						= "";
	private boolean						_wieghtBased				= false;
	private long						_sequenceNo					= 0L;
	private Date						_updatedDate				= new Date();
	private Date						_createdDate				= new Date();
	//private PublishType					_publishType				= PublishType.IMMEDIATE;
	//private Attachment					attachment					= null;
	//private ExtendedAssignmentInfo		_extensionInfo				= new ExtendedAssignmentInfo();
	private String						_proctorPassword			= null;
	private boolean						isAttempted					= false;
	boolean								reworkForStudent			= false;
	boolean								connectReworkForStudent		= false;								// added
																											// for
																											// new
																											// BOPA
																											// policy
	boolean								gradeByParticipation		= false;								// added
																											// for
																											// grade
																											// by
																											// participation
	// policy
	boolean									percentageScore				= false;
	boolean									nonAutoGradableAssignment	= false;
	// assignment stats
	private float						maxScore					= -1;
	private float						minScore					= -1;
	private float						avgScore					= -1;
	// assignment studyguide and prerequisite data
	private String						studyGuideName				= null;
	private String						structureId					= null;
	private String						prerequisiteId				= null;
	private float						prerequisiteScore			= 0F;
	private String						studyGuideFullName			= null;
	private final ArrayList					activityList				= new ArrayList();
	private final Activity[]					activities					= new Activity[0];
	// unfinished assignment prerequisite ids.
	private long[]						unfinishedPrerequisites		= new long[0];
	private float						points						= -1;									// Property
																											// points
																											// has
																											// been
																											// changed
																											// to
																											// float
																											// to
	// support fractional points for Ez Test.
	private int							AttemptsAllowed				= -1;
	private int							attemptsTaken				= 0;
	private int							attemptsLeft				= -1;
	private int							questions					= 0;
	private int							availableQuestions			= -1;
	private int							timelimit					= 0;
	private int							countStudents				= 0;
	private int							inProgressCount				= 0;
	private boolean						dummyStartDate				= false;

	private boolean						dummyStartDateByCourseTZ	= false;
	private boolean						dummyDueDate				= false;

	private boolean						dummyDueDateByCourseTZ		= false;

	private boolean						policyExceptions			= false;

	//private HMAssignmentPolicyException	studentExceptions			= null;

	private String							syncStatus					= null;
	
	//private ResourceStatus.SyncStatus 	lmsSyncStatus = null;

	private String						uUID						= null;

	private boolean						isLibraryAssignment			= false;

	private long							moduleID					= 0L;

	// WebLinks
	private WebLink[]						webLinks					= null;

	private long							parentAssignmentId			= 0L;
	
	private Long							sourceAssignmentId			= 0L;

	private long							parentSectionId				= 0L;

	//private HMAssignmentCategoryPolicy		policies					= null;
	private String							contentProvider				= null;
	private String							ebookReadingURL				= null;

	private String							libraryType					= null;
	private boolean							manuallyGraded;
	private int								lateSubmitValue				= -1;

	private boolean							isLinked					= false;

	private String							defaultGradeBookCategory	= "";

	private String						moduleType;
	// In Case of PBB.
	private long						copiedFromAssignmentId		= 0;
	private String						assignmentTypeIconClass;
	private long						version						= 1L;
	private boolean 					c15safe=true;
	private boolean						lmsDeployEnabled;
	private boolean						lmsPaired;
	private boolean						singleReadingOrTextFlow;
	private boolean						tcDeployed;
	private boolean managedDate = false;
	private String toolId;
	private String						toolDrafts				= null;
	private String  					toolPolicy				= null;
	private boolean						toolManagedDraft		= false;
	private boolean						toolManagedPeerReview	= false;
	private Date						toolPeerReviewDueDate		= new Date();
	private boolean						toolManagedAttempt		= false;
	private Date						toolDraftDueDate		= new Date();
	private String 						displayTitle;
	private boolean      				genericAssignment       = false;
	private Boolean      				isLateSubmissionEnabled;
	private final Date						lateSubmissionDue		= null;
	private Date						rawLateSubmissionDue    = new Date();
	
	public boolean isToolManagedPeerReview() {
		return toolManagedPeerReview;
	}

	public void setToolManagedPeerReview(boolean toolManagedPeerReview) {
		this.toolManagedPeerReview = toolManagedPeerReview;
	}

	public Date getToolPeerReviewDueDate() {
		return toolPeerReviewDueDate;
	}

	public void setToolPeerReviewDueDate(Date toolPeerReviewDueDate) {
		this.toolPeerReviewDueDate = toolPeerReviewDueDate;
	}

	public Boolean isLateSubmissionEnabled() {
		return isLateSubmissionEnabled;
	}

	public void setLateSubmissionEnabled(Boolean isLateSubmissionEnabled) {
		this.isLateSubmissionEnabled = isLateSubmissionEnabled;
	}

	public Date getLateSubmissionDue() {
		return lateSubmissionDue;
	}

	public void setLateSubmissionDue(Date lateSubmissionDue) {
		logger.debug("Late submission due value from Assignment VO : {}",lateSubmissionDue);
		// format Date to EST
		/*if (null == lateSubmissionDue) {
			this.lateSubmissionDue = null;
		} else {
			this.lateSubmissionDue = DateUtil.format(lateSubmissionDue, DateUtil.DB_TIMEZONE_ID);
			}*/
	}
	
	public void setRawLateSubmissionDueDate(Date rawLateSubmissionDue) {
		this.rawLateSubmissionDue = rawLateSubmissionDue;
	}

	public Date getRawLateSubmissionDueDate() {
		return this.rawLateSubmissionDue;
	}
	
	// for LTIA only
	private boolean isLtiaExternalLinkDeleted = false;
	private boolean isLtiaExternalLinkFailed = false;

	public boolean getIsLtiaExternalLinkDeleted() {
		return this.isLtiaExternalLinkDeleted;
	}
	public void setIsLtiaExternalLinkDeleted(boolean b) {
		this.isLtiaExternalLinkDeleted = b;
	}

	public boolean getIsLtiaExternalLinkFailed() {
		return this.isLtiaExternalLinkFailed;
	}
	public void setIsLtiaExternalLinkFailed(boolean b) {
		this.isLtiaExternalLinkFailed = b;
	}

	public boolean isGenericAssignment() {
		return genericAssignment;
	}

	public void setGenericAssignment(boolean genericAssignment) {
		this.genericAssignment = genericAssignment;
	}

	public String getDisplayTitle() {
		return displayTitle;
	}

	public void setDisplayTitle(String displayTitle) {
		this.displayTitle = displayTitle;
	}

	public Date getToolDraftDueDate() {
		return toolDraftDueDate;
	}

	public void setToolDraftDueDate(Date toolDraftDueDate) {
		this.toolDraftDueDate = toolDraftDueDate;
	}

	public boolean isToolManagedDraft() {
		return toolManagedDraft;
	}

	public void setToolManagedDraft(boolean toolManagedDraft) {
		this.toolManagedDraft = toolManagedDraft;
	}
	
	public boolean isToolManagedAttempt() {
		return toolManagedAttempt;
	}

	public void setToolManagedAttempt(boolean toolManagedAttempt) {
		this.toolManagedAttempt = toolManagedAttempt;
	}
	
	//Added for LTIA integration
	private String customLineitemsUrl = null;


	/**
	 * @return the customLineitemsUrl
	 */
	public String getCustomLineitemsUrl() {
		return customLineitemsUrl;
	}

	/**
	 * @param customLineitemsUrl the customLineitemsUrl to set
	 */
	public void setCustomLineitemsUrl(String customLineitemsUrl) {
		this.customLineitemsUrl = customLineitemsUrl;
	}
	//end -- Added for LTIA integration
	
	public String getToolPolicy() {
		return toolPolicy;
	}

	public void setToolPolicy(String toolPolicy) {
		this.toolPolicy = toolPolicy;
	}

	public String getToolDrafts() {
		return toolDrafts;
	}

	public void setToolDrafts(String toolDrafts) {
		this.toolDrafts = toolDrafts;
	}

	public boolean isManagedDate() {
		return managedDate;
	}

	public void setManagedDate(boolean managedDate) {
		this.managedDate = managedDate;
	}

	public boolean isLmsDeployEnabled() {
		return lmsDeployEnabled;
	}

	public void setLmsDeployEnabled(boolean lmsDeployEnabled) {
		this.lmsDeployEnabled = lmsDeployEnabled;
	}
	
	public boolean isC15safe() {
		return c15safe;
	}

	public void setC15safe(boolean c15safe) {
		this.c15safe = c15safe;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public long getGbCategoryId() {
		return gbCategoryId;
	}

	public void setGbCategoryId(long gbCategoryId) {
		this.gbCategoryId = gbCategoryId;
	}

	private long	gbCategoryId;

	public String getModuleType() {
		return moduleType;
	}

	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}

	public String getDefaultGradeBookCategory() {
		return defaultGradeBookCategory;
	}

	public void setDefaultGradeBookCategory(String defaultGradeBookCategory) {
		this.defaultGradeBookCategory = defaultGradeBookCategory;
	}

	public String getAssignmentId() {
		return assignmentId;
	}

	public void setAssignmentId(String assignmentId) {
		this.assignmentId = assignmentId;
	}

	private String							assignmentId			= "";
	// Content policy
	private boolean							hasContentPolicies;
	private boolean							areContentPoliciesDirty;
	// For video type assignment.
	private boolean							isPeerReview			= false;
	private Date							peerReviewDueDate;
	private boolean							isPastPeerReviewDueDate	= false;
	private boolean							isSelfReview			= false;

	//private AdditionalResponseMaterial[]	additionalResponse		= null;
	private boolean							peerReviewDummyDueDate	= false;

	private Date							inClassStartDate;
	private boolean							inClassDummyStartDate	= false;
	private String							assignmentType;
	private boolean							isAleEnabled			= false;
	private boolean							manualGradeRequired		= false;
	private long							currentSectionId		= 0;
	private String							currentUserId			= null;
	private long							producerId				= 0L;
	private long							consumerId				= 0L;
	private boolean							lmsDeployOn;
	
	private String 							scoreCalculationType;
	
	// Added Attempt enabled policy flag for Generic assignments.
	private boolean      					genericAttemptEnabled = false;
	// Added Late submission with due date enabled  policy flag.
	private boolean      					lateSubmissionEnabledWithdue       = false;
	
	public boolean isLateSubmissionEnabledWithdue() {
		return lateSubmissionEnabledWithdue;
	}

	public void setLateSubmissionEnabledWithdue(boolean lateSubmissionEnabledWithdue) {
		this.lateSubmissionEnabledWithdue = lateSubmissionEnabledWithdue;
	}

	public boolean isLmsDeployOn() {
		return lmsDeployOn;
	}

	public void setLmsDeployOn(boolean lmsDeployOn) {
		this.lmsDeployOn = lmsDeployOn;
	}

	public void setAssignmentReferenceId(String referenceId) {
		if (!stringIsBlankOrNull(referenceId)) {
			this.assignmentReferenceId = referenceId;
		}
	}

	public String getAssignmentReferenceId() {
		return getNonNullString(assignmentReferenceId);
	}

	private boolean						isAssignmentLinked						= false;
	private String						overWriteMySectionPolicies				= null;
	private String						overWriteColleaguesPolicies				= null;
	private String						currentUserRole							= null;
	//private List<Section>				destinationSections						= null;
	private long						destinationId;
	//private Destination					destination;										// optional - this is when
																							// an assignment
	// belongs to a Destination,like country
	//private Scenario					scenario;											// optional - this is when
																							// an assignment belongs
	// to a Scenario
	private Module						libraryModule;
	// this is the original Module that the parent of this assignment belonged to
	private String						notificationFlag						= "NO";
	private String						sourceInstructorId						= "";
	// added for Blackboard
	private boolean						isBbDeployed;
	private boolean						bbDeployOn;
	private boolean						isChatAssignment;
	private long						fingerprint								= 0L;
	// Added for Learnsmart diagnostic integration.
	private boolean						isLearnSmartDiagnosticEnabled;

	/*
	 * Added 1 property for showing the flag in assignment reports for answer tolerance.
	 */
	private boolean						isToleranceChangedForActiveAssignment	= false;
	private long						selfRubricId							= 0L;
	private long						peerRubricId							= 0L;
	private long						instRubricId							= 0L;
	private int							noOfDrafts								= 0;
	private Date						minDueDate;
	// Added to store the minimum start date
	private Date						minStartDate;
	//private AssignmentPolicyException	assignmentPolicyException;
	String								originalAttemptsAllowed					= null;
	String								originalTimeLimitInMinutes				= null;
	String								originalDelayFeedBack					= null;
	private String						oldtitle;
	// added for Q3- to get express grade status of the assignment
	private boolean						isExpressGraded							= false;

	/*
	 * added for Assignment Builder, to show delayed feedback and time limit and attempt allowed for an assignments
	 */
	public String						delayedFeedback							= null;
	public String						geniusMode							= null;
	public String						assignBuilderTimeLimit					= null;
	public String						assignBuilderAttemptsAllowed			= null;

	private String						lockedStatus;

	private long						productId								= 0L;

	private String						productTemplate;

	private String						productPlatform;

	private String						productIcon;

	private String						productIconTitle;

	private String						assignmentProductName;

	// added for lms deployed status
	private boolean						lmsDeployed;
	
	//private Map<Long, ResponseDetail>  peersUploadStatusMap;
	

	public String getAssignmentProductName() {
		return assignmentProductName;
	}

	public void setAssignmentProductName(String assignmentProductName) {
		this.assignmentProductName = assignmentProductName;
	}

	public String getProductIconTitle() {
		return productIconTitle;
	}

	public void setProductIconTitle(String productIconTitle) {
		this.productIconTitle = productIconTitle;
	}

	/**
	 * @return the productId
	 */
	public long getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getProductTemplate() {
		return productTemplate;
	}

	public void setProductTemplate(String productTemplate) {
		this.productTemplate = productTemplate;
	}

	public String getProductPlatform() {
		return productPlatform;
	}

	public void setProductPlatform(String productPlatform) {
		this.productPlatform = productPlatform;
	}

	public String getProductIcon() {
		return productIcon;
	}

	public void setProductIcon(String productIcon) {
		this.productIcon = productIcon;
	}

	// To get time limit for an assignment.
	public String getAssignBuilderTimeLimit() {
		return assignBuilderTimeLimit;
	}

	// To set time limit for an assignment.
	public void setAssignBuilderTimeLimit(String assignBuilderTimeLimit) {
		this.assignBuilderTimeLimit = assignBuilderTimeLimit;
	}

	// To get AttemptsAllowed for an assignment.
	public String getAssignBuilderAttemptsAllowed() {
		return assignBuilderAttemptsAllowed;
	}

	// To set AttemptsAllowed for an assignment.
	public void setAssignBuilderAttemptsAllowed(String assignBuilderAttemptsAllowed) {
		this.assignBuilderAttemptsAllowed = assignBuilderAttemptsAllowed;
	}

	/*
	 * corrupted: Flag for cheking assignment is currepted Currently checking only multiple activity and zero activity
	 */
	private boolean	corrupted;

	public boolean isCorrupted() {
		return corrupted;
	}

	public void setCorrupted(boolean corrupted) {
		this.corrupted = corrupted;
	}

	// for In class/online, in case of speechCapture assignment
	public enum ASSIGNMENT_MODE {
		INCLASS, ONLINE
    }

	private String					assignmentMode		= null;

	private AssignmentLineItem[]	assignmentLineItems	= null;



	public boolean isAdaptiveStudyPlan() {
		return adaptiveStudyPlan;
	}

	public void setAdaptiveStudyPlan(boolean adaptiveStudyPlan) {
		this.adaptiveStudyPlan = adaptiveStudyPlan;
	}

	//private String	peerReviewStatus	= STATUS.NOTSTARTED.name();
	//private String	selfReviewStatus	= STATUS.NOTSTARTED.name();

	public boolean isToleranceChangedForActiveAssignment() {
		return isToleranceChangedForActiveAssignment;
	}

	public void setToleranceChangedForActiveAssignment(boolean isToleranceChangedForActiveAssignment) {
		this.isToleranceChangedForActiveAssignment = isToleranceChangedForActiveAssignment;
	}

	public boolean isConnectReworkForStudent() {
		return connectReworkForStudent;
	}

	public void setConnectReworkForStudent(boolean connectReworkForStudent) {
		this.connectReworkForStudent = connectReworkForStudent;
	}

	public boolean isGradeByParticipation() {
		return gradeByParticipation;
	}

	public void setGradeByParticipation(boolean gradeByParticipation) {
		this.gradeByParticipation = gradeByParticipation;
	}

	/**
	 * @return the bbDeployOn
	 */
	public boolean isBbDeployOn() {
		return bbDeployOn;
	}

	/**
	 * @param bbDeployOn the bbDeployOn to set
	 */
	public void setBbDeployOn(boolean bbDeployOn) {
		this.bbDeployOn = bbDeployOn;
	}

	/**
	 * @return the isBbDeployed
	 */
	public boolean isBbDeployed() {
		return isBbDeployed;
	}

	/**
	 * @param isBbDeployed the isBbDeployed to set
	 */
	public void setBbDeployed(boolean isBbDeployed) {
		this.isBbDeployed = isBbDeployed;
	}

	public String getSourceInstructorId() {
		return sourceInstructorId;
	}

	public void setSourceInstructorId(String sourceInstructorId) {
		this.sourceInstructorId = sourceInstructorId;
	}

	public String getNotificationFlag() {
		return notificationFlag;
	}

	public void setNotificationFlag(String notificationFlag) {
		this.notificationFlag = notificationFlag;
	}



	public String getCurrentUserRole() {
		return currentUserRole;
	}

	public void setCurrentUserRole(String currentUserRole) {
		this.currentUserRole = currentUserRole;
	}

	public String getOverWriteMySectionPolicies() {
		return overWriteMySectionPolicies;
	}

	public void setOverWriteMySectionPolicies(String overWriteMySectionPolicies) {
		this.overWriteMySectionPolicies = overWriteMySectionPolicies;
	}

	public String getOverWriteColleaguesPolicies() {
		return overWriteColleaguesPolicies;
	}

	public void setOverWriteColleaguesPolicies(String overWriteColleaguesPolicies) {
		this.overWriteColleaguesPolicies = overWriteColleaguesPolicies;
	}

	public boolean isAssignmentLinked() {
		return isAssignmentLinked;
	}

	public void setAssignmentLinked(boolean isAssignmentLinked) {
		this.isAssignmentLinked = isAssignmentLinked;
	}

	public void setCurrentSectionId(long currentSectionId) {
		this.currentSectionId = currentSectionId;
	}

	public String getCurrentUserId() {
		return currentUserId;
	}

	public void setCurrentUserId(String currentUserId) {
		this.currentUserId = currentUserId;
	}

	public long getCurrentSectionId() {
		return currentSectionId;
	}

	public long[] getModuleIds() {
		return moduleIds;
	}

	public void setModuleIds(long[] moduleIds) {
		this.moduleIds = moduleIds;
	}

	public long[] getSectionIds() {
		return sectionIds;
	}

	public void setSectionIds(long[] sectionIds) {
		this.sectionIds = sectionIds;
	}

	public void setProducerId(long producerId) {
		if (producerId > 0L) {
			this.producerId = producerId;
		}
	}

	public void setConsumerId(long consumerId) {
		if (consumerId > 0L) {
			this.consumerId = consumerId;
		}
	}

	public long getProducerId() {
		return producerId;
	}

	public long getConsumerId() {
		return consumerId;
	}

	private long[]	sectionIds	= new long[0];
	private long[]	moduleIds	= new long[0];

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	private String	moduleName;

	public void setCourseID(long courseID) {
		this.courseID = courseID;
	}

	private long	courseID;

	public void setPublishingOption(String publishingOption) {
		this.publishingOption = publishingOption;
	}

	private String	publishingOption;

	public void setRedirectURL(String redirectURL) {
		this.redirectURL = redirectURL;
	}

	private String	redirectURL;

	// Force Grade Attribute
	private boolean	forceGrade	= false;

	public long getParentAssignmentId() {
		return parentAssignmentId;
	}

	public void setParentAssignmentId(long parentAssignmentId) {
		this.parentAssignmentId = parentAssignmentId;
	}
	
	public Long getSourceAssignmentId() {
		return sourceAssignmentId;
	}

	public void setSourceAssignmentId(Long sourceAssignmentId) {
		this.sourceAssignmentId = sourceAssignmentId;
	}
	
	public long getParentSectionId() {
		return parentSectionId;
	}

	public void setParentSectionId(long parentSectionId) {
		this.parentSectionId = parentSectionId;
	}

	public long getModuleID() {
		return moduleID;
	}

	public void setModuleID(long moduleID) {
		this.moduleID = moduleID;
	}

	public boolean getShowAssignment() {
		return showAssignment;
	}

	public void setShowAssignment(boolean showHide) {
		this.showAssignment = showHide;
	}

	private boolean	showAssignment	= true;

	public int getInProgressCount() {
		if (inProgressStudentNames != null) {
			return inProgressStudentNames.length;
		}
		return 0;
	}

	public void setInProgressCount(int inProgressCount) {
		this.inProgressCount = inProgressCount;
	}

	public String[] getActiveStudentNames() {
		return activeStudentNames;
	}

	public void setActiveStudentNames(String[] activeStudentNames) {
		this.activeStudentNames = activeStudentNames;
	}

	public String[] getInProgressStudentNames() {
		return inProgressStudentNames;
	}

	public void setInProgressStudentNames(String[] inProgressStudentNames) {
		this.inProgressStudentNames = inProgressStudentNames;
	}

	private String[]	activeStudentNames		= new String[0];

	private String[]	inProgressStudentNames	= new String[0];

	public boolean isStudentActive() {
		return studentActive;
	}

	public void setStudentActive(boolean studentActive) {
		this.studentActive = studentActive;
	}

	private boolean	studentActive		= false;
	private boolean	future				= false;
	private boolean	flagAssignment		= false;
	// This flag will be true if peer part of the assignment is going to be past
	// due within next 24hrs.
	private boolean	flagPeerAssignment	= false;

	public boolean isFlagAssignment() {
		return flagAssignment;
	}

	public void setFlagAssignment(boolean flagAssignment) {
		this.flagAssignment = flagAssignment;
	}

	public int getAttemptsAllowed() {
		return AttemptsAllowed;
	}

	public void setAttemptsAllowed(int attemptsAllowed) {
		AttemptsAllowed = attemptsAllowed;
	}

	public float getPoints() {
		return points;
	}

	public void setPoints(float points) {
		this.points = points;
	}

	public float getMinScore() {
		return minScore;
	}

	public void setMinScore(float minScore) {
		this.minScore = minScore;
	}

	public float getAvgScore() {
		return avgScore;
	}

	public void setAvgScore(float avgScore) {
		this.avgScore = avgScore;
	}


	public long getID() {
		return _id;
	}

	public void setID(long id) {
		this._id = id;
	}

	public String getCategory() {
		return _category;
	}

	public void setCategory(String category) {
		this._category = category;
	}

	public String getTitle() {
		/**
		 * TYPE CONFORMANCE CHECK FOR BAD DATA
		 */
		if (!stringIsBlankOrNull(this._title) && (this._title.indexOf("\n") > -1 || this._title.indexOf("\r") > -1)) {
			this._title = this._title.replaceAll("\n", " ");
			this._title = this._title.replaceAll("\r", " ");
		}
		return _title;
	}

	public void setTitle(String title) {
		this._title = title;
		/**
		 * TYPE CONFORMANCE CHECK FOR BAD DATA
		 */
		if (!stringIsBlankOrNull(this._title) && (this._title.indexOf("\n") > -1 || this._title.indexOf("\r") > -1)) {
			this._title = this._title.replaceAll("\n", " ");
			this._title = this._title.replaceAll("\r", " ");
		}
	}

	public static boolean stringIsBlankOrNull(String str) {
		return (str == null || str.trim().equals(""));
	}

	public static String getNonNullString(String str) {
		String returnValue = "";
		if (str != null) {
			returnValue = str;
		}
		return returnValue;
	}

	public String getDelayedFeedback() {
		return delayedFeedback;
	}

	public void setDelayedFeedback(String delayedFeedback) {
		this.delayedFeedback = delayedFeedback;
	}

	public Date getStartDate() {
		return _startDate;
	}

	public void setStartDate(Date startDate) {
		// format Date to EST
		if (null == startDate) {
			this._startDate = null;
		} else {
			this._startDate = DateUtil.format(startDate, DateUtil.DB_TIMEZONE_ID);
		}

	}

	public void setRawStartDate(Date startDate) {
		this._startDate = startDate;
	}

	public Date getRawStartDate() {
		return this._startDate;
	}

	public void setStartDate_assignProperties(Date startDate) {
		// format Date to EST
		this._startDate = startDate;
	}

	public Date getStartDateByCourseTZ() {
		return _startDateByCourseTZ;
	}

	public void setStartDateByCourseTZ(Date startDateByCourseTZ) {
		this._startDateByCourseTZ = startDateByCourseTZ;
	}

	public Date getDueDateByCourseTZ() {
		return _dueDateByCourseTZ;
	}

	public void setDueDateByCourseTZ(Date dueDateByCourseTZ) {
		this._dueDateByCourseTZ = dueDateByCourseTZ;
	}

	public Date getDueDate() {
		return _dueDate;
	}

	public void setDueDate(Date dueDate) {
		// format Date to EST
		if (null == dueDate) {
			this._dueDate = null;
		} else {
			this._dueDate = DateUtil.format(dueDate, DateUtil.DB_TIMEZONE_ID);
		}

	}

	public void setRawDueDate(Date dueDate) {
		this._dueDate = dueDate;
	}

	public Date getRawDueDate() {
		return this._dueDate;
	}

	public void setDueDate_assignProperties(Date dueDate) {
		// format Date to EST
		this._dueDate = dueDate;
	}

	public float getWeight() {
		return _wieght;
	}

	public void setWeight(float wieght) {
		this._wieght = wieght;
	}

	public float getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(float maxScore) {
		this.maxScore = maxScore;
	}


	public long getDuration() {
		return _duration;
	}

	public void setDuration(long duration) {
		this._duration = duration;
	}


	public String getNote() {
		if (_note == null) {
			_note = "";
		}
		return _note;
	}

	public void setNote(String note) {
		this._note = note;
	}

	public boolean isWeightBased() {
		return _wieghtBased;
	}

	public void setWeightBased(boolean wieghtBased) {
		this._wieghtBased = wieghtBased;
	}

	public long getSequenceNo() {
		return _sequenceNo;
	}

	public void setSequenceNo(long sequenceNo) {
		this._sequenceNo = sequenceNo;
	}

	public Date getUpdatedDate() {
		return _updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this._updatedDate = updatedDate;
	}

	public Date getCreatedDate() {
		return _createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this._createdDate = createdDate;
	}


	public String getProctorPassword() {
		return _proctorPassword;
	}

	public void setProctorPassword(String proctorPassword) {
		this._proctorPassword = proctorPassword;
	}

	public boolean isAttempted() {
		return isAttempted;
	}

	public void setAttempted(boolean attempted) {
		isAttempted = attempted;
	}

	public String getStudyGuideName() {
		return studyGuideName;
	}

	public void setStudyGuideName(String studyGuideName) {
		this.studyGuideName = studyGuideName;
	}

	public String getStructureId() {
		return structureId;
	}

	public void setStructureId(String structureId) {
		this.structureId = structureId;
	}

	public String getPrerequisiteId() {
		return prerequisiteId;
	}

	public void setPrerequisiteId(String prerequisiteId) {
		this.prerequisiteId = prerequisiteId;
	}

	public float getPrerequisiteScore() {
		return prerequisiteScore;
	}

	public void setPrerequisiteScore(float prerequisiteScore) {
		this.prerequisiteScore = prerequisiteScore;
	}

	public String getStudyGuideFullName() {
		return studyGuideFullName;
	}

	public void setStudyGuideFullName(String studyGuideFullName) {
		this.studyGuideFullName = studyGuideFullName;
	}

	public void addActivity(Activity act) {
		this.activityList.add(act);
	}

	public Activity[] getActivities() {
		return (Activity[]) activityList.toArray(new Activity[0]);
	}

	public void setUnfinishedPrerequisites(long[] prerequisiteIds) {
		if (prerequisiteIds != null && prerequisiteIds.length > 0) {
			unfinishedPrerequisites = new long[prerequisiteIds.length];
			System.arraycopy(prerequisiteIds, 0, unfinishedPrerequisites, 0, prerequisiteIds.length);
		}
	}

	public long[] getUnfinishedPrerequisites() {
		return this.unfinishedPrerequisites;
	}

	public String getAccess_level() {
		return access_level;
	}

	public void setAccess_level(String access_level) {
		this.access_level = access_level;
	}

	public String getPolicyOverridden() {
		return isPolicyOverridden;
	}

	public void setPolicyOverridden(String policyOverridden) {
		isPolicyOverridden = policyOverridden;
	}

	public long getLinked_to_assignment() {
		return linked_to_assignment;
	}

	public void setLinked_to_assignment(long linked_to_assignment) {
		this.linked_to_assignment = linked_to_assignment;
	}

	public String getPrimary_instructor_id() {
		return primary_instructor_id;
	}

	public void setPrimary_instructor_id(String primaryInstructorId) {
		this.primary_instructor_id = primaryInstructorId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getCategory_id() {
		return category_id;
	}

	public void setCategory_id(long category_id) {
		this.category_id = category_id;
	}

	/**
	 * BY Default Access Level is always Section. The values this variable can have are the following 1. course 2.
	 * section 3. multisection 4. None
	 */
	private String	access_level			= "";

	/**
	 * By default this will be NO. Will be changed if the instructor over rides any default policies provided by the
	 * system.
	 */
	private String	isPolicyOverridden		= "";

	/**
	 * This is the assignment id to which this assignment is linked. By default this will be -1. and Null will be
	 * persisted in the db
	 */
	private long	linked_to_assignment	= -1;

	/**
	 * Primary instructor id for the assignment. Defaulted to null for classware assignments but will not be null for
	 * homeworkmanager assignments.
	 */
	private String	primary_instructor_id	= "";

	/*
	 * Status of the assignment is hide by default.
	 */
	private String	status					= "";

	private long	category_id				= -1;

	public int getQuestions() {
		return questions;
	}

	public void setQuestions(int questions) {
		this.questions = questions;
	}

	// Added by Minakshi for Active Activity
	private boolean	isAssignmentActive	= false;

	public void setAssignmentActive(boolean activeAssignment) {
		this.isAssignmentActive = activeAssignment;
	}

	public boolean isAssignmentActive() {
		return this.isAssignmentActive;
	}

	public int getTimelimit() {
		return timelimit;
	}

	public void setTimelimit(int timelimit) {
		this.timelimit = timelimit;
	}

	public int getCountStudents() {
		return countStudents;
	}

	public void setCountStudents(int countStudents) {
		this.countStudents = countStudents;
	}

	public int getAttemptsTaken() {
		return attemptsTaken;
	}

	public void setAttemptsTaken(int attemptsTaken) {
		this.attemptsTaken = attemptsTaken;
	}

	private String		primarySection			= null;
	private String[]	secondarySection		= new String[0];
	private String		primaryInstructorName	= null;
	private String[]	secondaryInstructors	= new String[0];

	public String getPrimaryInstructorName() {
		return primaryInstructorName;
	}

	public void setPrimaryInstructorName(String primaryInstructorName) {
		this.primaryInstructorName = primaryInstructorName;
	}

	public String getPrimarySection() {
		return primarySection;
	}

	public void setPrimarySection(String primarySection) {
		this.primarySection = primarySection;
	}

	public String[] getSecondaryInstructors() {
		return secondaryInstructors;
	}

	public void setSecondaryInstructors(String[] secondaryInstructors) {
		this.secondaryInstructors = secondaryInstructors;
	}

	public String[] getSecondarySection() {
		return secondarySection;
	}

	public void setSecondarySection(String[] secondarySection) {
		this.secondarySection = secondarySection;
	}

	public void setTimeRemaining(long l) {
		// To change body of created methods use File | Settings | File
		// Templates.
		this.timeRemainingInSeconds = l;
	}

	public long getTimeRemaining() {
		return this.timeRemainingInSeconds;
	}

	private long	timeRemainingInSeconds	= 0L;

	public boolean isPasswordRequired() {
		return passwordRequired;
	}

	public void setPasswordRequired(boolean passwordRequired) {
		this.passwordRequired = passwordRequired;
	}

	private boolean	passwordRequired	= false;
	private boolean	strictDue			= false;

	public boolean isStrictDue() {
		return strictDue;
	}

	public void setStrictDue(boolean strictDue) {
		this.strictDue = strictDue;
	}

	public boolean isFuture() {
		return future;
	}

	public void setFuture(boolean future) {
		this.future = future;
	}

	public boolean checkDummyStartDate() {
		try {
			String tz = DateUtil.DB_TIMEZONE_ID;
			Date sDate = GenUtil.getDefaultDate(tz, true);
			if (logger.isDebugEnabled()) {
				logger.debug("Assignment Id " + _id);
				logger.debug("Default start date is " + sDate);
				logger.debug("Assignment Start date is : " + _startDate);
			}
			if (_startDate.equals(sDate)) {
				this.setDummyStartDate(true);
				return true;
			}
		} catch (Exception e) {
			logger.debug("Exception in setting DefaultDate setDefaultDate()" + e);
		}
		return false;
	}

	public boolean checkDummyDueDate() {
		try {
			String tz = DateUtil.DB_TIMEZONE_ID;
			Date eDate = GenUtil.getDefaultDate(tz, false);
			if (logger.isDebugEnabled()) {
				logger.debug("Assignment Id " + _id);
				logger.debug("Default end date is " + eDate);
				logger.debug("Assignment due date : " + _dueDate);
			}
			if (_dueDate.equals(eDate)) {
				this.setDummyDueDate(true);
				return true;
			}
		} catch (Exception e) {
			logger.debug("Exception in setting DefaultDate setDefaultDate()" + e);
		}
		return false;
	}

	public boolean checkDummyStartDateByCourseTZ() {
		try {
			String tz = DateUtil.DB_TIMEZONE_ID;
			Date sDate = GenUtil.getDefaultDate(tz, true);
			if (logger.isDebugEnabled()) {
				logger.debug("Assignment Id " + _id);
				logger.debug("Default start date is " + sDate);
				logger.debug("Assignment Start date by course TZ is : " + _startDateByCourseTZ);
			}
			if (_startDateByCourseTZ.equals(sDate)) {
				this.setDummyStartDateByCourseTZ(true);
				return true;
			}
		} catch (Exception e) {
			logger.debug("Exception in setting DefaultDate setDefaultDate()" + e);
		}
		return false;
	}

	public boolean checkDummyDueDateByCourseTZ() {
		try {
			String tz = DateUtil.DB_TIMEZONE_ID;
			Date eDate = GenUtil.getDefaultDate(tz, false);
			if (logger.isDebugEnabled()) {
				logger.debug("Assignment Id " + _id);
				logger.debug("Default end date is " + eDate);
				logger.debug("Assignment due date course TZ : " + _dueDateByCourseTZ);
			}
			if (_dueDateByCourseTZ.equals(eDate)) {
				this.setDummyDueDateByCourseTZ(true);
				return true;
			}
		} catch (Exception e) {
			logger.debug("Exception in setting DefaultDate setDefaultDate()" + e);
		}
		return false;
	}



	public int compareTo(Object assignment) {
		if (!(assignment instanceof Assignment assignmentToCompare)) {
			throw new ClassCastException("Input object not an assignment");
		}

        if (!(this.getDueDate().equals(assignmentToCompare.getDueDate()))) {
			if (this.getDueDate().before(assignmentToCompare.getDueDate())) {
				return -1;
			}
			else {
				return 1;
			}
		} else if (!(this.getTitle().equals(assignmentToCompare.getTitle()))) {
			return this.getTitle().compareToIgnoreCase(assignmentToCompare.getTitle());
		} else if (this.getID() < assignmentToCompare.getID()) {
			return -1;
		} else {
			return 1;
		}
	}

	public String getSyncStatus() {
		return syncStatus;
	}

	public void setSyncStatus(String syncStatus) {
		this.syncStatus = syncStatus;
	}

	public boolean isPolicyExceptions() {
		return policyExceptions;
	}

	public void setPolicyExceptions(boolean policyExceptions) {
		this.policyExceptions = policyExceptions;
	}



	public boolean isDummyDueDate() {
		return dummyDueDate;
	}

	public void setDummyDueDate(boolean dummyDueDate) {
		this.dummyDueDate = dummyDueDate;
	}

	public boolean isDummyStartDate() {
		return dummyStartDate;
	}

	public void setDummyStartDate(boolean dummyStartDate) {
		this.dummyStartDate = dummyStartDate;
	}

	public boolean isDummyStartDateByCourseTZ() {
		return dummyStartDateByCourseTZ;
	}

	public void setDummyStartDateByCourseTZ(boolean dummyStartDateByCourseTZ) {
		this.dummyStartDateByCourseTZ = dummyStartDateByCourseTZ;
	}

	public boolean isDummyDueDateByCourseTZ() {
		return dummyDueDateByCourseTZ;
	}

	public void setDummyDueDateByCourseTZ(boolean dummyDueDateByCourseTZ) {
		this.dummyDueDateByCourseTZ = dummyDueDateByCourseTZ;
	}

	public boolean isReworkForStudent() {
		return reworkForStudent;
	}

	public void setReworkForStudent(boolean reworkForStudent) {
		this.reworkForStudent = reworkForStudent;
	}

	public boolean isNonAutoGradableAssignment() {
		return nonAutoGradableAssignment;
	}

	public void setNonAutoGradableAssignment(boolean nonAutoGradableAssignment) {
		this.nonAutoGradableAssignment = nonAutoGradableAssignment;
	}

	public String getType() {
		return _type;
	}

	public void setType(String type) {
		this._type = type;
	}

	public boolean isPercentageScore() {
		return percentageScore;
	}

	public void setPercentageScore(boolean percentageScore) {
		this.percentageScore = percentageScore;
	}

	public int getAttemptsLeft() {
		return attemptsLeft;
	}

	public void setAttemptsLeft(int attemptsLeft) {
		this.attemptsLeft = attemptsLeft;
	}

	public String getUuid() {
		return uUID;
	}

	public void setUuid(String uuid) {
		this.uUID = uuid;
	}

	public boolean isLibraryAssignment() {
		return isLibraryAssignment;
	}

	public void setLibraryAssignment(boolean isLibraryAssignment) {
		this.isLibraryAssignment = isLibraryAssignment;
	}

	public WebLink[] getWebLinks() {
		return webLinks;
	}

	public void setWebLinks(WebLink[] webLinks) {
		this.webLinks = webLinks;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the isbn
	 */
	public String getIsbn() {
		return isbn;
	}

	/**
	 * @param isbn the isbn to set
	 */
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}


	public String getEbookReadingURL() {
		return ebookReadingURL;
	}

	public void setEbookReadingURL(String ebookReadingURL) {
		this.ebookReadingURL = ebookReadingURL;
	}

	public String getContentProvider() {
		return contentProvider; // To change body of created methods use File |
		// Settings | File Templates.
	}

	public void setContentProvider(String provider) {
		contentProvider = provider;
		// To change body of created methods use File | Settings | File Templates.
	}

	public String getRedirectURL() {
		return redirectURL;
	}

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getPreviewURL() {
		return previewURL;
	}

	public void setPreviewURL(String previewURL) {
		this.previewURL = previewURL;
	}

	public void setNativeAlaId(String newNativeAlaId) {
		this.nativeAlaId = newNativeAlaId;
	}

	public String getNativeAlaId() {
		return this.nativeAlaId;
	}

	private String	nativeAlaId	= null;

	public String getPublishingOption() {
		return publishingOption;
	}

	public long getCourseID() {
		return courseID;
	}

	public String getLibraryType() {
		return libraryType;
	}

	public void setLibraryType(String libraryType) {
		this.libraryType = libraryType;
	}

	public String getModuleName() {
		return moduleName;
	}

	/* Added by Tricon for student assignment portlet */
	private boolean	isPastDueDate	= false;

	public boolean isPastDueDate() {
		return isPastDueDate;
	}

	public void setPastDueDate(boolean isPastDueDate) {
		this.isPastDueDate = isPastDueDate;
	}

	public boolean isLinked() {
		return isLinked;
	}

	public void setLinked(boolean isLinked) {
		this.isLinked = isLinked;
	}

	public Date getFormattedStartDate() {
		return formattedStartDate;
	}

	public void setFormattedStartDate(Date formatedStartDate) {
		this.formattedStartDate = formatedStartDate;
	}

	public Date getFormattedDueDate() {
		return formattedDueDate;
	}

	public void setFormattedDueDate(Date formatedDueDate) {
		this.formattedDueDate = formatedDueDate;
	}

	public boolean getHasContentPolicies() {
		return hasContentPolicies;
	}

	public void setHasContentPolicies(boolean hasContentPolicies) {
		this.hasContentPolicies = hasContentPolicies;
	}

	public boolean getAreContentPoliciesDirty() {
		return areContentPoliciesDirty;
	}

	public void setAreContentPoliciesDirty(boolean areContentPoliciesDirty) {
		this.areContentPoliciesDirty = areContentPoliciesDirty;
	}

	public int getAvailableQuestions() {
		return availableQuestions;
	}

	public void setAvailableQuestions(int availableQuestions) {
		this.availableQuestions = availableQuestions;
	}

	/**
	 * <p>
	 * Get the isPeerReview.
	 * </p>
	 * 
	 * @return the isPeerReview.
	 * @see #isPeerReview
	 */

	public boolean isPeerReview() {
		return isPeerReview;
	}

	/**
	 * <p>
	 * Set the isPeerReview.
	 * </p>
	 * 
	 * @param isPeerReview new value for the isPeerReview field.
	 * @see #isPeerReview
	 */

	public void setPeerReview(boolean isPeerReview) {
		this.isPeerReview = isPeerReview;
	}

	/**
	 * <p>
	 * Get the peerReviewDueDate.
	 * </p>
	 * 
	 * @return the peerReviewDueDate.
	 * @see #peerReviewDueDate
	 */

	public Date getPeerReviewDueDate() {
		return peerReviewDueDate;
	}

	/**
	 * <p>
	 * Set the peerReviewDueDate.
	 * </p>
	 * 
	 * @param peerReviewDueDate new value for the peerReviewDueDate field.
	 * @see #peerReviewDueDate
	 */

	public void setPeerReviewDueDate(Date peerReviewDueDate) {
		this.peerReviewDueDate = DateUtil.format(peerReviewDueDate, DateUtil.DB_TIMEZONE_ID);
	}

	public void setRawPeerReviewDueDate(Date peerReviewDueDate) {
		this.peerReviewDueDate = peerReviewDueDate;
	}



	/**
	 * <p>
	 * Get the peerReviewDummyDueDate.
	 * </p>
	 * 
	 * @return the peerReviewDummyDueDate.
	 * @see #peerReviewDummyDueDate
	 */
	public boolean isPeerReviewDummyDueDate() {
		return peerReviewDummyDueDate;
	}

	/**
	 * <p>
	 * Set the peerReviewDummyDueDate.
	 * </p>
	 * 
	 * @param peerReviewDummyDueDate new value for the peerReviewDummyDueDate field.
	 * @see #peerReviewDummyDueDate
	 */
	public void setPeerReviewDummyDueDate(boolean peerReviewDummyDueDate) {
		this.peerReviewDummyDueDate = peerReviewDummyDueDate;
	}

	/**
	 * <p>
	 * Method to check peer review dummy date is set or not
	 * </p>
	 * 
	 * @return
	 */
	public boolean checkPeerReviewDummyDate() {
		try {
			String tz = DateUtil.DB_TIMEZONE_ID;
			Date sDate = GenUtil.getDefaultDate(tz, false);
			if (!isPeerReview || peerReviewDueDate == null || peerReviewDueDate.equals(sDate)) {
				this.setPeerReviewDummyDueDate(true);
				return true;
			}
		} catch (Exception e) {
			logger.debug("Exception in setting DefaultDate setDefaultDate()" + e);
		}
		return false;
	}

	public boolean isPastPeerReviewDueDate() {
		return isPastPeerReviewDueDate;
	}

	public void setPastPeerReviewDueDate(boolean isPastPeerReviewDueDate) {
		this.isPastPeerReviewDueDate = isPastPeerReviewDueDate;
	}

	public void setForceGrade(boolean b) {
		this.forceGrade = b;
	}

	public boolean isForceGrade() {
		return forceGrade;
	}




	public String getAssignmentType() {
		return assignmentType;
	}

	public void setAssignmentType(String assignmentType) {
		this.assignmentType = assignmentType;
	}

	public boolean isAleEnabled() {
		return isAleEnabled;
	}

	public void setAleEnabled(boolean isAleEnabled) {
		this.isAleEnabled = isAleEnabled;
	}

	public boolean isManualGradeRequired() {
		return manualGradeRequired;
	}

	public void setManualGradeRequired(boolean manualGradeRequired) {
		this.manualGradeRequired = manualGradeRequired;
	}



	public long getDestinationId() {
		return destinationId;
	}

	public void setDestinationId(long destinationId) {
		this.destinationId = destinationId;
	}



	public Module getLibraryModule() {
		return libraryModule;
	}

	public void setLibraryModule(Module libraryModule) {
		this.libraryModule = libraryModule;
	}

	/**
	 * <p>
	 * Get the manuallyGraded.
	 * </p>
	 * 
	 * @return the manuallyGraded.
	 * @see #manuallyGraded
	 */
	public boolean isManuallyGraded() {
		return manuallyGraded;
	}

	/**
	 * <p>
	 * Set the manuallyGraded.
	 * </p>
	 * 
	 * @param manuallyGraded new value for the manuallyGraded field.
	 * @see #manuallyGraded
	 */
	public void setManuallyGraded(boolean manuallyGraded) {
		this.manuallyGraded = manuallyGraded;
	}

	/**
	 * @return the isChatAssignment
	 */
	public boolean isChatAssignment() {
		return isChatAssignment;
	}

	/**
	 * @param isChatAssignment the isChatAssignment to set
	 */
	public void setChatAssignment(boolean isChatAssignment) {
		this.isChatAssignment = isChatAssignment;
	}
	/**
	 * @return the isSelfReview
	 */
	public boolean isSelfReview() {
		return isSelfReview;
	}

	/**
	 * @param isSelfReview the isSelfReview to set
	 */
	public void setSelfReview(boolean isSelfReview) {
		this.isSelfReview = isSelfReview;
	}

	/**
	 * @return the selfRubricId
	 */
	public long getSelfRubricId() {
		return selfRubricId;
	}

	/**
	 * @param selfRubricId the selfRubricId to set
	 */
	public void setSelfRubricId(long selfRubricId) {
		this.selfRubricId = selfRubricId;
	}

	/**
	 * @return the peerRubricId
	 */
	public long getPeerRubricId() {
		return peerRubricId;
	}

	/**
	 * @param peerRubricId the peerRubricId to set
	 */
	public void setPeerRubricId(long peerRubricId) {
		this.peerRubricId = peerRubricId;
	}

	/**
	 * @return the instRubricId
	 */
	public long getInstRubricId() {
		return instRubricId;
	}

	/**
	 * @param instRubricId the instRubricId to set
	 */
	public void setInstRubricId(long instRubricId) {
		this.instRubricId = instRubricId;
	}

	public long getFingerprint() {
		return fingerprint;
	}

	public void setFingerprint(long fingerprint) {
		this.fingerprint = fingerprint;
	}

	/**
	 * @return the assignmentMode
	 */
	public String getAssignmentMode() {
		return assignmentMode;
	}

	/**
	 * @param assignmentMode the assignmentMode to set
	 */
	public void setAssignmentMode(String assignmentMode) {
		if (ASSIGNMENT_MODE.INCLASS.name().equalsIgnoreCase(assignmentMode) || ASSIGNMENT_MODE.ONLINE.name().equalsIgnoreCase(assignmentMode)) {
			this.assignmentMode = assignmentMode;
		}
	}

	public AssignmentLineItem[] getAssignmentLineItems() {
		return assignmentLineItems;
	}

	public void setAssignmentLineItems(AssignmentLineItem[] assignmentLineItems) {
		this.assignmentLineItems = assignmentLineItems;
	}

	private String	paletteType	= null;

	public enum PALETTE_TYPE {
		SIMPLE, COMPLEX
	}

    public void setPaletteType(String paletteType) {
		if (PALETTE_TYPE.SIMPLE.name().equals(paletteType) || PALETTE_TYPE.COMPLEX.name().equals(paletteType)) {
			this.paletteType = paletteType;
		}
	}

	public String getPaletteType() {
		return this.paletteType;
	}

	private long				policyInstanceSetId;
	private PolicyInstanceSet	policyInstanceSet;

	public long getPolicyInstanceSetId() {
		return policyInstanceSetId;
	}

	public void setPolicyInstanceSetId(long policyInstanceSetId) {
		this.policyInstanceSetId = policyInstanceSetId;
	}

	public PolicyInstanceSet getPolicyInstanceSet() {
		return policyInstanceSet;
	}

	public void setPolicyInstanceSet(PolicyInstanceSet policyInstanceSet) {
		this.policyInstanceSet = policyInstanceSet;
	}

	private long	assignmentTypeId;

	public long getAssignmentTypeId() {
		return assignmentTypeId;
	}

	public void setAssignmentTypeId(long assignmentTypeId) {
		this.assignmentTypeId = assignmentTypeId;
	}

	/**
	 * @return the inClassStartDate
	 */
	public Date getInClassStartDate() {
		return inClassStartDate;
	}

	/**
	 * @param inClassStartDate the inClassStartDate to set
	 */
	public void setInClassStartDate(Date inClassStartDate) {
		this.inClassStartDate = DateUtil.format(inClassStartDate, DateUtil.DB_TIMEZONE_ID);
	}

	/**
	 * @return the inClassDummyStartDate
	 */
	public boolean isInClassDummyStartDate() {
		return inClassDummyStartDate;
	}

	/**
	 * @param inClassDummyStartDate the inClassDummyStartDate to set
	 */
	public void setInClassDummyStartDate(boolean inClassDummyStartDate) {
		this.inClassDummyStartDate = inClassDummyStartDate;
	}

	public void setFlagPeerAssignment(boolean flagPeerAssignment) {
		this.flagPeerAssignment = flagPeerAssignment;
	}

	public boolean isFlagPeerAssignment() {
		return flagPeerAssignment;
	}

	public boolean isLearnSmartDiagnosticEnabled() {
		return isLearnSmartDiagnosticEnabled;
	}

	public void setLearnSmartDiagnosticEnabled(boolean isLearnSmartDiagnosticEnabled) {
		this.isLearnSmartDiagnosticEnabled = isLearnSmartDiagnosticEnabled;
	}

	public int getLateSubmitValue() {
		return lateSubmitValue;
	}

	public void setLateSubmitValue(int lateSubmitValue) {
		this.lateSubmitValue = lateSubmitValue;
	}

	public Date getMinDueDate() {
		return minDueDate;
	}

	public void setMinDueDate(Date minDueDate) {
		this.minDueDate = minDueDate;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public void setNoOfDrafts(int noOfDrafts) {
		this.noOfDrafts = noOfDrafts;
	}

	public int getNoOfDrafts() {
		return noOfDrafts;
	}

	/**
	 * @return the minStartDate
	 */
	public Date getMinStartDate() {
		return minStartDate;
	}

	/**
	 * @param minStartDate the minStartDate to set
	 */
	public void setMinStartDate(Date minStartDate) {
		this.minStartDate = minStartDate;
	}



	public String getOriginalAttemptsAllowed() {
		return originalAttemptsAllowed;
	}

	public void setOriginalAttemptsAllowed(String originalAttemptsAllowed) {
		this.originalAttemptsAllowed = originalAttemptsAllowed;
	}

	public String getOriginalTimeLimitInMinutes() {
		return originalTimeLimitInMinutes;
	}

	public void setOriginalTimeLimitInMinutes(String originalTimeLimitInMinutes) {
		this.originalTimeLimitInMinutes = originalTimeLimitInMinutes;
	}

	public String getOriginalDelayFeedBack() {
		return originalDelayFeedBack;
	}

	public void setOriginalDelayFeedBack(String originalDelayFeedBack) {
		this.originalDelayFeedBack = originalDelayFeedBack;
	}

	public Date getDelayedFeedbackDate() {
		return _delayedFeedbackDate;
	}

	public void setDelayedFeedbackDate(Date delayedFeedbackDate) {
		this._delayedFeedbackDate = delayedFeedbackDate;
	}

	public String getOldtitle() {
		return oldtitle;
	}

	public void setOldtitle(String oldtitle) {
		this.oldtitle = oldtitle;
	}

	public String getLockedStatus() {
		return lockedStatus;
	}

	public void setLockedStatus(String lockedStatus) {
		this.lockedStatus = lockedStatus;
	}

	/**
	 * @param isLmsDeployed the isLmsDeployed to set
	 */
	public void setLmsDeployed(boolean isLmsDeployed) {
		this.lmsDeployed = isLmsDeployed;
	}

	/**
	 * @return the isLmsDeployed
	 */
	public boolean isLmsDeployed() {
		return lmsDeployed;
	}

	public long getCopiedFromAssignmentId() {
		return copiedFromAssignmentId;
	}

	public void setCopiedFromAssignmentId(long copiedFromAssignmentId) {
		this.copiedFromAssignmentId = copiedFromAssignmentId;
	}

	/**
	 * @return the isExpressGraded
	 */
	public boolean isExpressGraded() {
		return isExpressGraded;
	}

	/**
	 * @param isExpressGraded the isExpressGraded to set
	 */
	public void setExpressGraded(boolean isExpressGraded) {
		this.isExpressGraded = isExpressGraded;
	}

	/**
	 * @return the assignmentTypeIconClass
	 */
	public String getAssignmentTypeIconClass() {
		return assignmentTypeIconClass;
	}

	/**
	 * @param assignmentTypeIconClass the assignmentTypeIconClass to set
	 */
	public void setAssignmentTypeIconClass(String assignmentTypeIconClass) {
		this.assignmentTypeIconClass = assignmentTypeIconClass;
	}

	public String getGeniusMode() {
		return geniusMode;
	}

	public void setGeniusMode(String geniusMode) {
		this.geniusMode = geniusMode;
	}



	public boolean isLmsPaired() {
		return lmsPaired;
	}

	public void setLmsPaired(boolean lmsPaired) {
		this.lmsPaired = lmsPaired;
	}

	public boolean isSingleReadingOrTextFlow() {
		return singleReadingOrTextFlow;
	}

	public void setSingleReadingOrTextFlow(boolean singleReadingOrTextFlow) {
		this.singleReadingOrTextFlow = singleReadingOrTextFlow;
	}

	public boolean isTcDeployed() {
		return tcDeployed;
	}

	public void setTcDeployed(boolean tcDeployed) {
		this.tcDeployed = tcDeployed;
	}


	public String getScoreCalculationType() {
		return scoreCalculationType;
	}

	public void setScoreCalculationType(String scoreCalculationType) {
		this.scoreCalculationType = scoreCalculationType;
	}

	public String getToolId() {
		return toolId;
	}

	public void setToolId(String toolId) {
		this.toolId = toolId;
	}

	public boolean isGenericAttemptEnabled() {
		return genericAttemptEnabled;
	}

	public void setGenericAttemptEnabled(boolean genericAttemptEnabled) {
		this.genericAttemptEnabled = genericAttemptEnabled;
	}
	
	public boolean isProctoringEnabled() {
		return proctoringEnabled;
	}

	public void setProctoringEnabled(boolean proctoringEnabled) {
		this.proctoringEnabled = proctoringEnabled;
	}
	
}
