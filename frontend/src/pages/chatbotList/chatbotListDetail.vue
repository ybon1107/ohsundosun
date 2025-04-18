<script setup>
import Header from '../../components/Header.vue';
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRoute } from 'vue-router';
import { useFontSizeStore } from '../../api/fontsize'; // 폰트 사이즈 스토어 가져오기

const route = useRoute();
const id = route.params.id; // 동적 경로의 id 가져오기

// 폰트 사이즈 스토어 사용
const fontSizeStore = useFontSizeStore();
const fontSize = fontSizeStore.getFontSizeValue(); // 현재 폰트 사이즈 가져오기

// 메시지를 저장할 변수
const messages = ref([]);

// 메시지 가져오는 함수
const fetchMessages = async () => {
  try {
    const response = await axios.get(
      `http://localhost:8080/api/chatbotRoom/${id}`
    );
    messages.value = response.data; // 서버 응답 데이터를 messages에 저장
  } catch (error) {
    console.error('Error fetching messages:', error);
  }
};

// 컴포넌트가 마운트될 때 메시지 가져오기
onMounted(() => {
  fetchMessages();
});
</script>
<template>
  <Header />
  <div class="main-container chat-container">
    <div class="ms-3 me-3">
      <div
        class="message-container"
      >
        <div
          v-for="message in messages"
          :key="message.messageId"
          :class="[
            'card border-light p-4 mb-4',
            message.senderType === 'Bot'
              ? 'chatbot-response bg-light text-dark'
              : 'user-request bg-light text-dark ',
          ]"
          :style="{ fontSize }"
        >
          <div class="d-flex justify-content-between align-items-center mb-2">
            <span class="font-small">
              <img
                v-if="message.senderType === 'Bot'"
                class="avatar-sm img-fluid rounded-circle mr-2"
                src="@/assets/images/sooni.png"
                alt="avatar"
              />
              <span class="font-weight-bold small-font">
                {{ message.senderType === 'Bot' ? '순이' : '김대한' }}
              </span>
            </span>
            <span class="ml-auto text-end text-muted small-font">{{
              message.timestamp
            }}</span>
          </div>
          <p class="m-0">
            {{ message.messageText }}
          </p>
        </div>
      </div>
    </div>
  </div>
</template>
<style scoped>
.main-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between; /* 요소 간의 공간을 균등하게 분배 */
  height: calc(100vh - 70px); /* 화면에서 Header를 제외한 나머지 높이 */
  padding: 0 10px; /* 좌우 여백 추가 */
}

.message-container {
  width: 100%; /* 메시지 컨테이너의 너비를 100%로 설정 */
  max-width: 375px; /* 최대 너비를 375px로 설정 */
  overflow-y: auto; /* 세로 스크롤 추가 */
  overflow-x: hidden; /* 가로 스크롤 숨기기 */
  height: 100%; /* 높이를 100%로 설정하여 공간을 꽉 채움 */
}

.card {
  width: 100%; /* 카드의 너비를 100%로 설정 */
  box-sizing: border-box; /* 패딩과 보더를 포함한 너비 계산 */
  position: relative;
  display: flex;
  flex-direction: column;
  min-width: 0;
  word-wrap: break-word;
  background-color: #ffffff;
  background-clip: border-box;
  border: 0.0625rem solid rgba(0, 0, 0, 0.1);
  border-radius: 0.45rem;
}

.card-body {
  flex: 1 1 auto;
  min-height: 1px;
  padding: 1.5rem;
}
.avatar-sm {
  width: 1.5rem;
  height: 1.5rem;
}
.img-fluid {
  max-width: 100%;
  height: auto;
}
.rounded-circle {
  border-radius: 50% !important;
}
.mr-2 {
  margin-right: 0.5rem !important;
}
.font-weight-bold {
  font-weight: 600 !important;
}
.p-4 {
  padding: 1.5rem !important;
}
.text-white {
  color: #ffffff !important;
}
.bg-primary {
  background-color: #0d1b48 !important;
}

/* 챗봇 응답: 오른쪽 여백 추가 */
.chatbot-response {
  margin-left: auto; /* 자동으로 왼쪽에 여백 추가 */
  margin-right: 50px; /* 오른쪽 여백 */
  text-align: left; /* 왼쪽 정렬 */
}

/* 사용자 요청: 왼쪽 여백 추가 */
.user-request {
  margin-right: auto; /* 자동으로 오른쪽에 여백 추가 */
  margin-left: 50px; /* 왼쪽 여백 */
  text-align: left; /* 왼쪽 정렬 */
}

.bg-white {
  background-color: white !important;
}
.border-light {
  border-color: #edf0f7 !important;
}
.chat-container {
  display: flex;
  flex-direction: column-reverse; /* 메시지를 아래에서 위로 쌓기 */
}

.small-font {
  font-size: 1rem; /* 고정된 작은 폰트 사이즈 설정 */
}
</style>
