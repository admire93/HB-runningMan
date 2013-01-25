import java.util.Date

import play.api._
import play.api.mvc._
import play.api.mvc.Results._

import anorm._

import models._

object Global extends GlobalSettings {

  override def onHandlerNotFound(request: RequestHeader): Result = {
    implicit val flash = request.flash
    NotFound(
      views.html.notfound()
    )
  }

  override def onStart(app: Application) {
    val now = new Date
    if(Question.findAll.isEmpty) {
      List[Question](
        Question(
          NotAssigned,
          "bible",
          "10계명 4개 이상 말하세요.",
          "10게명 4개이상 맞는지 체크해주세요."
        ),
        Question(
          NotAssigned,
          "bible",
          "하나님의 말씀을 전파하다, 돌에 맞아 죽은 사람은 누구인가요?",
          "스데반 집사"
        ),
        Question(
          NotAssigned,
          "bible",
          "하나님께서는 천지 창조 마지막날엔 무엇을 하셨을까요?",
          "안식일 쉬셨음."
        ),
        Question(
          NotAssigned,
          "bible",
          "예수님을 은 30냥에 팔아넘긴 인물은?",
          "가롯 유다"
        ),
        Question(
          NotAssigned,
          "bible",
          "예수님께서 베드로에게 '네가 나를 사랑하느냐?'라고 몇번 물으셨나요.",
          "3번"
        ),
        Question(
          NotAssigned,
          "bible",
          "성경의 가장 첫번째 책과 마지막 책",
          "창세기, 요한 계시록"
        ),
        Question(
          NotAssigned,
          "bible",
          "모세가 쓴 5권의 성경은?",
          "창세기 출애굽기 레위기 민수기 신명기"
        ),
        Question(
          NotAssigned,
          "bible",
          "롯과 롯의 아내가 살다가, 멸망된 성이름은?",
          "소돔과 고모라 성"
        ),
        Question(
          NotAssigned,
          "bible",
          "아브라함의 아들 이름은?",
          "이삭"
        ),
        Question(
          NotAssigned,
          "bible",
          "성경 구약성서, 신약성서 중 6개 이상 말하기",
          "맞는지확인해주세요."
        ),
        Question(
          NotAssigned,
          "bible",
          "아담과 하와가 따먹은 열매는?",
          "선악과"
        ),
        Question(
          NotAssigned,
          "bible",
          "아담과 하와가 살던 동산이름?",
          "에덴동산"
        ),
        Question(
          NotAssigned,
          "bible",
          "다니엘은 몇명의 친구가 있었을까요?",
          "3명"
        ),
        Question(
          NotAssigned,
          "bible",
          "유다왕국을 멸망시킨 바벨론의 왕은?",
          "느부갓네살 왕"
        ),
        Question(
          NotAssigned,
          "bible",
          "노아의 3아들들의 이름을 말하세요",
          "셈과 함과 야벳"
        ),
        Question(
          NotAssigned,
          "bible",
          "롯의 아내는 뒤를 돌아보다 무엇으로 변했나요?",
          "소금기둥"
        ),
        Question(
          NotAssigned,
          "bible",
          "예수님은 무엇에 달려 돌아가셨나요?",
          "십자가"
        ),
        Question(
          NotAssigned,
          "bible",
          "예수님의 제자들의 수는?",
          "12명"
        ),
        Question(
          NotAssigned,
          "bible",
          "오병이어의 기적에서 5000명을 다먹고도 몇 광주리가 남았다고했습니다. 몇광주리일까요?",
          "12광주리"
        ),
        Question(
          NotAssigned,
          "bible",
          "키가 작아서 나무에 올라가서 예수님을 보던 사람의 이름은?",
          "삭개오"
        ),
        Question(
          NotAssigned,
          "bible",
          "예수님덕분에 눈을 뜨게된 장님의 이름은?",
          "바디메오"
        ),
        Question(
          NotAssigned,
          "bible",
          "예수님이 몇일만에 부활하셨나요?",
          "3일(사흘로 얘기하면 몇일인지 꼭물어봐주세요)"
        ),
        Question(
          NotAssigned,
          "bible",
          "사도바울의 이전 이름은?",
          "사울"
        ),
        Question(
          NotAssigned,
          "bible",
          "요셉이 투옥됬을때, 두명의 관원이 같이있었습니다. 두명의 관원은 무엇을 하는 관원이였나요?",
          "떡맡은관원, 술맡은관원"
        ),
        Question(
          NotAssigned,
          "bible",
          "요셉은 종살이시절 억울한 누명을 쓰고 감옥에 투옥됩니다. 어떤 누명이였나요?",
          "보디발장군의 아내가 유혹하다가 실패하니 요셉이 강제로 동침하려했다고 누명을 씌움."
        ),
        Question(
          NotAssigned,
          "bible",
          "출 애굽 당시, 이스라엘 백성들에게 하나님이 내려주셨던 음식 2가지.",
          "만나, 메추라기"
        ),
        Question(
          NotAssigned,
          "bible",
          "모세는 몇가지의 재앙을 애굽에 내렸을까요?",
          "10가지"
        ),
        Question(
          NotAssigned,
          "action",
          "뱀",
          ""
        ),
        Question(
          NotAssigned,
          "action",
          "돼지",
          ""
        ),
        Question(
          NotAssigned,
          "action",
          "소",
          ""
        ),
        Question(
          NotAssigned,
          "action",
          "할아버지",
          ""
        ),
        Question(
          NotAssigned,
          "action",
          "원숭이",
          ""
        ),
        Question(
          NotAssigned,
          "action",
          "결혼",
          ""
        ),
        Question(
          NotAssigned,
          "action",
          "십자가",
          ""
        ),
        Question(
          NotAssigned,
          "action",
          "예수님",
          ""
        ),
        Question(
          NotAssigned,
          "action",
          "모세",
          ""
        ),
        Question(
          NotAssigned,
          "action",
          "사랑",
          ""
        ),
        Question(
          NotAssigned,
          "action",
          "기타",
          ""
        ),
        Question(
          NotAssigned,
          "action",
          "피아노",
          ""
        ),
        Question(
          NotAssigned,
          "action",
          "잠(Sleep)",
          ""
        ),
        Question(
          NotAssigned,
          "action",
          "휴대폰",
          ""
        ),
        Question(
          NotAssigned,
          "action",
          "식사",
          ""
        ),
        Question(
          NotAssigned,
          "action",
          "얼짱",
          ""
        ),
        Question(
          NotAssigned,
          "action",
          "모자",
          ""
        ),
        Question(
          NotAssigned,
          "action",
          "교회",
          ""
        ),
        Question(
          NotAssigned,
          "action",
          "노트북",
          ""
        ),
        Question(
          NotAssigned,
          "action",
          "지팡이",
          ""
        ),
        Question(
          NotAssigned,
          "action",
          "악마 or 마귀 or 사탄",
          ""
        ),
        Question(
          NotAssigned,
          "action",
          "찬양",
          ""
        ),
        Question(
          NotAssigned,
          "action",
          "자동차",
          ""
        ),
        Question(
          NotAssigned,
          "action",
          "비행기",
          ""
        ),
        Question(
          NotAssigned,
          "action",
          "런닝맨",
          ""
        ),
        Question(
          NotAssigned,
          "action",
          "만세",
          ""
        ),
        Question(
          NotAssigned,
          "action",
          "태권도",
          ""
        ),
        Question(
          NotAssigned,
          "action",
          "농구",
          ""
        ),
        Question(
          NotAssigned,
          "action",
          "축구",
          ""
        ),
        Question(
          NotAssigned,
          "action",
          "배구",
          ""
        ),
        Question(
          NotAssigned,
          "speak",
          "내가 그린 기린 그림은 잘 그린 기린 그림이고 니가 그린 기린 그림은 잘 못 그린 기린 그림이다.",
          ""
        ),
        Question(
          NotAssigned,
          "speak",
          "저기 계신 저 분이 박 법학박사이시고 여기 계신 이 분이 백 법학박사이시다.",
          ""
        ),
        Question(
          NotAssigned,
          "speak",
          "간장 공장 공장장은 강 공장장이고 된장 공장 공장장은 공 공장장이다.",
          ""
        ),
        Question(
          NotAssigned,
          "speak",
          " 집 팥죽은 붉은 팥 풋팥죽이고 , 뒷집 콩죽은 햇콩단콩 콩죽,우리집 깨죽은 검은깨 깨죽인데 사람들은 햇콩 단콩 콩죽 깨죽 죽먹기를 싫어하더라.",
          ""
        ),
        Question(
          NotAssigned,
          "speak",
          "우리집 옆집 앞집 뒷창살은 흩겹창살이고, 우리집 뒷집 앞집 옆창살은 겹흩창살이다.",
          ""
        ),
        Question(
          NotAssigned,
          "speak",
          "작은 토끼 토끼통 옆 큰 토끼 토끼통 큰 토끼 토끼통 옆 작은 토끼 토끼통",
          ""
        ),
        Question(
          NotAssigned,
          "speak",
          "신인 샹송 가수의 신춘 샹송 쇼",
          ""
        ),
        Question(
          NotAssigned,
          "speak",
          "들의 콩깍지는 깐 콩깍지인가 안 깐 콩깍지인가? 깐 콩깍지면 어떻고 안 깐 콩깍지면 어떠냐? 깐 콩깍지나 안 깐 콩깍지나 콩깍지는 다 콩깍지인데",
          ""
        )
      ).map { q =>
        Question.add(q) 
      }
    }

    if(Mission.findAll.isEmpty) {
      List[Mission](
        Mission(
          NotAssigned,
          "강도사님을 이겨라",
          "강도사님을 탁구로 이기세요! 1명이 3점내기로 강도사님을 이기면 미션이 종료됩니다.",
          "교육관 무대 앞",
          now
        ),
        Mission(
          NotAssigned,
          "볼링",
          "볼링을 쳐서 성공시키세요! 단 단순한 볼링은 아닙니다 :-)",
          "교육관 출구 앞",
          now
        ),
        Mission(
          NotAssigned,
          "성경 퀴즈!!!",
          "성경 퀴즈를 각자 한문제씩 30초안에 맞춰주세요. :~|",
          "본관 유아실",
          now
        ),
        Mission(
          NotAssigned,
          "여러번 말해요",
          "주어진 문장을 20초안에 3번씩 읽어주세요.",
          "본관 성가대",
          now
        ),
        Mission(
          NotAssigned,
          "몸으로 말해요.",
          "30초안에 주어진 단어를 몸으로 설명해주세요.",
          "교육관",
          now
        )
      ).map { mission =>
        Mission.add(mission)
      }
    }
  }  

}