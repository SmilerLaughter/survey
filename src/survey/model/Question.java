package survey.model;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable{

	private Integer questionId;
	private Integer questionTypeId;
	private String title;
	private List<Option> options;
	private int otherType;
	private boolean isMaxtri;
	private List<OtherOption> otherSelecOptions;
	private List<MaxtrixRow> maxtrixRows;
	private List<MaxtrixCol> maxtrixCols;
	private List<MaxtriOption> maxtrixSelectOptions;
	private Integer pageId;
	private String optionStr;
	private String otherOptionStr;
	private String maxtrixRowStr;
	private String maxtrixColStr;
	private String maxtrixOptionStr;


	public void setMaxtrixOptionStr(String maxtrixOptionStr) {
		this.maxtrixOptionStr = maxtrixOptionStr;
	}
	
	public String getMaxtrixOptionStr() {
		return maxtrixOptionStr;
	}

	@Override
	public String toString() {
		return "Question [questionId=" + questionId + ", questionTypeId=" + questionTypeId + ", title=" + title
				+ ", options=" + options + ", otherType=" + otherType + ", isMaxtri=" + isMaxtri
				+ ", otherSelecOptions=" + otherSelecOptions + ", maxtrixRows=" + maxtrixRows + ", maxtrixCols="
				+ maxtrixCols + ", maxtrixSelectOptions=" + maxtrixSelectOptions + ", pageId=" + pageId + ", optionStr="
				+ optionStr + ", otherOptionStr=" + otherOptionStr + ", maxtrixRowStr=" + maxtrixRowStr
				+ ", maxtrixColStr=" + maxtrixColStr + "]";
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public Integer getQuestionTypeId() {
		return questionTypeId;
	}

	public void setQuestionTypeId(Integer questionTypeId) {
		this.questionTypeId = questionTypeId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}

	public int getOtherType() {
		return otherType;
	}

	public void setOtherType(int otherType) {
		this.otherType = otherType;
	}

	public boolean isMaxtri() {
		return isMaxtri;
	}

	public void setMaxtri(boolean isMaxtri) {
		this.isMaxtri = isMaxtri;
	}

	public List<OtherOption> getOtherSelecOptions() {
		return otherSelecOptions;
	}

	public void setOtherSelecOptions(List<OtherOption> otherSelecOptions) {
		this.otherSelecOptions = otherSelecOptions;
	}

	public List<MaxtrixRow> getMaxtrixRows() {
		return maxtrixRows;
	}

	public void setMaxtrixRows(List<MaxtrixRow> maxtrixRows) {
		this.maxtrixRows = maxtrixRows;
	}

	public List<MaxtrixCol> getMaxtrixCols() {
		return maxtrixCols;
	}

	public void setMaxtrixCols(List<MaxtrixCol> maxtrixCols) {
		this.maxtrixCols = maxtrixCols;
	}

	public List<MaxtriOption> getMaxtrixSelectOptions() {
		return maxtrixSelectOptions;
	}

	public void setMaxtrixSelectOptions(List<MaxtriOption> maxtrixSelectOptions) {
		this.maxtrixSelectOptions = maxtrixSelectOptions;
	}

	public Integer getPageId() {
		return pageId;
	}

	public void setPageId(Integer pageId) {
		this.pageId = pageId;
	}

	public String getOptionStr() {
		return optionStr;
	}

	public void setOptionStr(String optionStr) {
		this.optionStr = optionStr;
	}

	public String getOtherOptionStr() {
		return otherOptionStr;
	}

	public void setOtherOptionStr(String otherOptionStr) {
		this.otherOptionStr = otherOptionStr;
	}

	public String getMaxtrixRowStr() {
		return maxtrixRowStr;
	}

	public void setMaxtrixRowStr(String maxtrixRowStr) {
		this.maxtrixRowStr = maxtrixRowStr;
	}

	public String getMaxtrixColStr() {
		return maxtrixColStr;
	}

	public void setMaxtrixColStr(String maxtrixColStr) {
		this.maxtrixColStr = maxtrixColStr;
	}

	public Question(Integer questionTypeId, String title, List<Option> options, int otherType, boolean isMaxtri,
			List<OtherOption> otherSelecOptions, List<MaxtrixRow> maxtrixRows, List<MaxtrixCol> maxtrixCols,
			List<MaxtriOption> maxtrixSelectOptions, Integer pageId, String optionStr, String otherOptionStr,
			String maxtrixRowStr, String maxtrixColStr) {
		super();
		this.questionTypeId = questionTypeId;
		this.title = title;
		this.options = options;
		this.otherType = otherType;
		this.isMaxtri = isMaxtri;
		this.otherSelecOptions = otherSelecOptions;
		this.maxtrixRows = maxtrixRows;
		this.maxtrixCols = maxtrixCols;
		this.maxtrixSelectOptions = maxtrixSelectOptions;
		this.pageId = pageId;
		this.optionStr = optionStr;
		this.otherOptionStr = otherOptionStr;
		this.maxtrixRowStr = maxtrixRowStr;
		this.maxtrixColStr = maxtrixColStr;
	}

	public Question() {
		// TODO Auto-generated constructor stub
	}

}