package edu.kh.budget.model.dto;

public class Budget {
	private int budgetNo;
	private String budgetContent;
	private String budgetOption;
	private int budgetAmount;
	private int budgetLeft;
	
	//기본 생성자
	public Budget() {}

	//매개변수 생성자
	public Budget(int budgetNo, String budgetContent, String budgetOption, int budgetAmount, int budgetLeft) {
		super();
		this.budgetNo = budgetNo;
		this.budgetContent = budgetContent;
		this.budgetOption = budgetOption;
		this.budgetAmount = budgetAmount;
		this.budgetLeft = budgetLeft;
	}

	//getter/setter
	public String getBudgetContent() {
		return budgetContent;
	}

	

	public int getBudgetNo() {
		return budgetNo;
	}

	public void setBudgetNo(int budgetNo) {
		this.budgetNo = budgetNo;
	}

	public void setBudgetContent(String budgetContent) {
		this.budgetContent = budgetContent;
	}

	public String getBudgetOption() {
		return budgetOption;
	}

	public void setBudgetOption(String budgetOption) {
		this.budgetOption = budgetOption;
	}

	public int getBudgetAmount() {
		return budgetAmount;
	}

	public void setBudgetAmount(int budgetAmount) {
		this.budgetAmount = budgetAmount;
	}

	public int getBudgetLeft() {
		return budgetLeft;
	}

	public void setBudgetLeft(int budgetLeft) {
		this.budgetLeft = budgetLeft;
	}

	//toString
	@Override
	public String toString() {
		return "Budget [budgetContent=" + budgetContent + ", budgetOption=" + budgetOption + ", budgetAmount="
				+ budgetAmount + ", budgetLeft=" + budgetLeft + "]";
	}
	
	
	
	
	
	
	
	
	
	
}
