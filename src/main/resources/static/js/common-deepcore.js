//해당연도
const years = [2018,2019,2020]
function getYears(callback){
	callback(years)
}
const months = [01,02,03,04,05,06,07,08,09,10,11,12]
function getMonths(callback){
	callback(months)
}
const orderState = [['매수주문',1],['매수완료',2],['매수주문취소',3],['체결',1],['미체결',2],['부분매도',3]];
function getOrderState(callback){
	callback(orderState);
}
// 선물상품명
function getFutureProduct(callback){
	axios({
		method:'get',
		url:'/api/basic/future/list'
	}).then(function(response){
		const res = response.data.content.map(m=>m.futureName);
		callback(res,response.data.content);
	}).catch(function(reason){
        checkError(reason);
    });
}
//선물상품명
function getFutureProductAll(callback){
	axios({
		method:'get',
		url:'/api/basic/future/list/all'
	}).then(function(response){
		callback(response.data.futureNameList)
	}).catch(function(reason){
        checkError(reason);
    });
}
//계좌번호 전체
function getAccountNumberAll(callback){
	axios({
		method:'get',
		url: '/api/basic/finance/account/info/list/all',
	}).then(function(response){
		callback(response.data.accountNumberList);
	}).catch(function(reason){
        checkError(reason);
    });
}
// 계좌번호
function getAccountNumber(callback){
	axios({
		method:'get',
		url: '/api/basic/finance/account/info/list',
	}).then(function(response){
		//const res = response.content.map(c=>c.accountNumber);
		callback(response);
	}).catch(function(reason){
        checkError(reason);
    });
}

function getAccountInfoAcctNoList(callback){
	axios({
		method:'get',
		url: '/api/search/fnclorgnllist/list',
	}).then((response)=>{ 
			const result = response.data.reduce((sum,v)=>{
				sum['fnclOrgnNm'].includes(v.fnclOrgnNm) ? '' : sum['fnclOrgnNm'].push(v.fnclOrgnNm);
				sum['fnclOrgnCd'].includes(v.fnclOrgnCd) ? '' : sum['fnclOrgnCd'].push(v.fnclOrgnCd);
				sum['fnclOrgnDvsnCd'].includes(v.fnclOrgnDvsnCd) ? '' : sum['fnclOrgnDvsnCd'].push(v.fnclOrgnDvsnCd);
				return sum;
			},{'fnclOrgnNm':[],'fnclOrgnCd':[],'fnclOrgnDvsnCd':[]});
			callback(result);
		}).catch(function(reason){
        checkError(reason);
    });	
}

function getCommonValidValueList(callback){
	axios({
		method:'get',
		url: '/api/search/commonvalidvaluelist/list',
	}).then((response)=>{ 
			callback(response.data);
		}).catch(function(reason){
        checkError(reason);
    });	
}

function getTranRgstDtList(callback) {
	axios({
		method: 'get',					
		url: '/api/basic/future/trade/all',
	}).then(function(response){
		callback(response.data.futureTradeInfoSearchList);
	}).catch(function(error){
		checkError(error);
	});
}
function getManageList(callback) {
	axios({
		method: 'get',
		url: '/api/basic/issue/manage/nopagelist',
	}).then(function(response){
		callback(response.data.manageList);
	}).catch(function(error){
		checkError(error);
		layout.hideLoading();
	});
}

function getFutureTradeList(callback){
	axios({
		method: 'get',
		url: '/api/basic/future/trade/all',
	}).then(function(response){
		callback(response.data.futureTradeInfoSearchList);
	}).catch(function(error){
		checkError(error);
		layout.hideLoading();
	});	
}

function getAccountHistoryList(callback){
	axios({
		method: 'get',
		url: '/api/basic/finance/account/history/list',
	}).then(function(response){
		callback(response.data.content);
	}).catch(function(error){
		checkError(error);
		layout.hideLoading();
	});		
}

//거래
function getFnclorgnlList(callback){
	axios({
		method: 'get',
		url: '/api/search/fnclorgnllist/list',
	}).then(function(response){
		callback(response.data);
	}).catch(function(error){
		checkError(error);
		layout.hideLoading();
	});		
}

//포트폴리오 종목
function getPortfolioIssueListNotNationCode(portfolioId, callback) {
	if(portfolioId == null || portfolioId == '') {
		callback([]);
		return;
	}

    axios({
        method: 'get',
        url: '/api/basic/port/issue/searchlist/' + portfolioId 
    }).then(function(response) {
    	var list = response.data;
    	if(list.length > 0) {
    		for(var x = 0 ; x < list.length ; x++) {
    			var info = list[x];
    			info.issueName = info.issueInfo.issueName;
    		}
    	}
        callback(list);
    }).catch(function(reason){
        checkError(reason);
    });
}

function getAccountHistory(){
	axios({
		method: 'get',
		url: '/api/basic/finance/account/history',
	}).then(function(response){
//		callback(response.data);
	}).catch(function(error){
		checkError(error);
		layout.hideLoading();
	});			
}
// 금융기관 구분 코드 FNCL_ORGN_CD api/common/code/valid/value/FNCL_ORGN_CD
function getFnclOrgnCdList(FNCL_ORGN_CD,callback){
	axios({
		method: 'get',
		url: '/api/common/code/valid/value/' + FNCL_ORGN_CD
	}).then(function(response){
		callback(response.data);
	}).catch(function(error){
		checkError(error);
		layout.hideLoading();
	});			
}

//그리드 클릭행 배경색 강조  
var TableBackgroundNormalColor = "#ffffff";  
var TableBackgroundMouseoverColor = "#9999ff";  

function ChangeBackgroundColor(row) {  
    row.style.backgroundColor = TableBackgroundMouseoverColor;  
}  
function RestoreBackgroundColor(row) {  
    row.style.backgroundColor = TableBackgroundNormalColor;  
}

// 전일 날짜값 산출
function getYesterday() {
	var date = new Date();
	
	var year = date.getFullYear();
	var month = (1 + date.getMonth());
	month = month >= 10 ? month : '0' + month; // MM을 두자리로 설정
	var day = date.getDate();
	day = day >= 10 ? day : '0' + day;
	day = day - 1;
	return year + '-' + month + '-' + day;
}

//당일 날짜값 산출
function getToday() {
	var date = new Date();
	
	var year = date.getFullYear();
	var month = (1 + date.getMonth());
	month = month >= 10 ? month : '0' + month; // MM을 두자리로 설정
	var day = date.getDate();
	day = day >= 10 ? day : '0' + day;
	return year + '-' + month + '-' + day;
}

//숫자필드 자릿수 구분
