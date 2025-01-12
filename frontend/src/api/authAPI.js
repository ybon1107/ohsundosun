import api from '@/api';

const BASE_URL = '/api/member';
const headers = { 'Content-Type': 'application/json' };

export default {
  async create(member) {
    const formData = new FormData();
    formData.append('username', member.username);
    formData.append('password', member.password);

    const { data } = await api.post(BASE_URL, formData, { headers });
    console.log('회원가입 정보: ', data); //서버 응답 로그

    return data;
  },
};
