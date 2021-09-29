<template>
  <!-- 播放标签 -->
      <div class="player">
        <aplayer :audio="audio" :lrcType="1" :fixed=true :mini=true :autoplay =true />
      </div>
</template>

<script>
export default {
  name: 'audio',
  data () {
    return {
      //1468248736  464916877   死神1851022762
      id: 464916877,
      audio: { // 初始歌曲数据
        name: 'さくら ~あなたに出会えてよかった~（樱花樱花想见你）',
        artist: '君と一緒にいればいい / 佐天Tamako',
        url: '',
        cover: 'https://p2.music.126.net/CqX6GHTRypSI76yETcRC8A==/109951162874963136.jpg/',
        lrc: '[00:00.000] 作词 : 高野健一\\n[00:01.000] 作曲 : 高野健一\\n[00:04.00]\\n[00:05.99]さくら さくら 会いたいよ\\n[00:08.41]いやだ 君に今すぐ 会いたいよ\\n[00:11.79]だいじょうぶ もう泣かないで\\n[00:14.40]私は風 あなたを包んでいるよ\\n[00:17.97]さくら さくら 会いたいよ\\n[00:20.33]いやだ 君に今すぐ 会いたいよ\\n[00:23.90]ありがとう ずっと大好き\\n[00:26.39]私は星 あなたを見守り続ける\\n[00:30.97]あなたに出会えて よかった\\n[00:36.06]本当に本当に よかった\\n[00:39.67]\\n[00:42.58]ここにもういれなくなっちゃった もう\\n[00:45.31]行かなくちゃ ホントゴメンね\\n[00:48.11]私はもう一人で遠いところに行かなくちゃ\\n[00:54.56]どこへ って聞かないで なんでって\\n[00:57.10]聞かないでホントゴメンね\\n[01:00.02]私はもうあなたのそばにいられなくなったの\\n[01:06.51]いつもの散歩道桜並木を抜けてゆき\\n[01:12.16]よく遊んだ川面の上の空の光る方へと\\n[01:18.47]もう会えなくなるけど寂しいけど 平気だよ\\n[01:23.65]生まれてよかった\\n[01:25.26]ホントよかった\\n[01:26.66]あなたに出会ってよかった\\n[01:29.99]さくら さくら 会いたいよ\\n[01:32.16]いやだ 君に今すぐ 会いたいよ\\n[01:35.91]だいじょうぶ もう泣かないで\\n[01:38.29]私は風 あなたを 包んでいるよ\\n[01:41.97]さくら さくら 会いたいよ\\n[01:44.27]いやだ 君に今すぐ 会いたいよ\\n[01:47.72]ありがとう ずっと大好き\\n[01:50.26]私は星 あなたを見守り 続ける\\n[01:54.82]あなたに出会えてよかった\\n[01:59.81]本当に本当によかった\\n[02:03.85]\\n[02:06.42]あなたの帰りを待つ午後\\n[02:08.51]あなたの足音 何げないこと\\n[02:11.83]私はそう一番の喜びを知りました\\n[02:18.16]あなたが話してくれたこと\\n[02:20.98]一日のこと いろいろなこと\\n[02:24.17]私はそう一番の悲しみも知りました\\n[02:30.27]それはあなたの笑顔あなたの涙 その優しさ\\n[02:36.37]私の名を呼ぶ声 抱き締める腕 その温もり\\n[02:42.35]もう触れられないけど\\n[02:44.52]忘れないよ 幸せだよ\\n[02:47.74]生まれてよかった\\n[02:49.14]ホントよかった\\n[02:50.67]あなたに出会ってよかった\\n[02:54.04]さくら さくら 会いたいよ\\n[02:56.31]いやだ 君に今すぐ会いたいよ\\n[02:59.74]だいじょうぶだよ ここにいる\\n[03:02.50]私は春 あなたを抱く空\\n[03:06.14]さくら さくら 会いたいよ\\n[03:08.20]いやだ 君に今すぐ会いたいよ\\n[03:11.78]ありがとう ずっと大好き\\n[03:14.23]私は鳥 あなたに歌い続ける\\n[03:21.31]\\n[03:24.19]桜の舞う空の彼方\\n[03:30.03]目を閉じれば心の中\\n[03:36.42]\\n[03:41.95]さくら さくら 会いたいよ\\n[03:44.22]いやだ 君に今すぐ会いたいよ\\n[03:47.89]いいんだよ 微笑んでごらん\\n[03:50.36]私は花 あなたの指先の花\\n[03:54.03]さくら さくら 会いたいよ\\n[03:56.10]いやだ 君に今すぐ会いたいよ\\n[03:59.79]ありがとう ずっと大好き\\n[04:02.30]私は愛 あなたの胸に\\n[04:06.05]\\n[04:08.96]さくら さくら 会いたいよ\\n[04:11.07]いやだ 君に今すぐ 会いたいよ\\n[04:14.87]だいじょうぶ もう泣かないで\\n[04:17.23]私は風 あなたを 包んでいるよ\\n[04:20.86]さくら さくら 会いたいよ\\n[04:23.07]いやだ 君に今すぐ 会いたいよ\\n[04:26.80]ありがとう ずっと大好き\\n[04:29.07]私は星 あなたを見守り\\n[04:34.43]続ける\\n[04:35.38]あなたに出会えて\\n[04:37.13]よかった\\n[04:40.50]本当に本当に よかった\\n[04:46.54]本当に本当に よかった\\n[04:53.80]\\n',
        autoplay: true
      }
    }
  },
  created () {
    this.$root.$on('songId', (value) => {
      console.log(value)
      this.playMusic(value)
    })
    // 获取歌曲信息
    this.$http({
      url: '/music/song/detail',
      method: 'get',
      params: {
        ids: this.id
      }
    }).then(res => {
      this.audio.name = res.data.songs[0].name
      this.audio.artist = res.data.songs[0].ar[0].name
      this.audio.cover = res.data.songs[0].al.picUrl
      console.log(this.audio.name)
    })
    // 获取歌词
    this.$http({
      url: '/music/lyric',
      method: 'get',
      params: {
        id: this.id
      }
    }).then(res => {
      const lrc = this.audio.lrc = res.data.lrc.lyric
      this.audio.lrc = lrc
    })
    // }
    // 获取歌曲音源
    this.$http({
      url: '/music/song/url',
      method: 'get',
      params: {
        id: this.id
      }
    }).then(res => {
      const url = res.data.data[0].url
      console.log(res.data.data[0].url)
      // 设置播放地址
      this.audio.url = url
    })
  },
  methods: {
    playMusic (songId) {
      this.$http({
        url: '/music/song/detail',
        method: 'get',
        params: {
          ids: songId
        }
      }).then(res => {
        this.audio.name = res.data.songs[0].name
        this.audio.artist = res.data.songs[0].ar[0].name
        this.audio.cover = res.data.songs[0].al.picUrl
        console.log(this.audio.name)
      })
      // 获取歌词
      this.$http({
        url: '/music/lyric',
        method: 'get',
        params: {
          id: songId
        }
      }).then(res => {
        const lrc = this.audio.lrc = res.data.lrc.lyric
        this.audio.lrc = lrc
      })
      // }
      // 获取歌曲音源
      this.$http({
        url: '/music/song/url',
        method: 'get',
        params: {
          id: songId
        }
      }).then(res => {
        const url = res.data.data[0].url
        console.log(res.data.data[0].url)
        // 设置播放地址
        this.audio.url = url
      })
    }
  }
}
</script>

<style scoped>

</style>
