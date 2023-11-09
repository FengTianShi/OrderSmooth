<template>
    <div>
        <form>
            <div v-if="!isShowOtpInput">
                <b-form-input class="mb-3" type="email" placeholder="お名前"></b-form-input>

                <b-form-input class="mb-3" type="email" placeholder="メールアドレス"></b-form-input>

                <b-form-input class="mb-3" type="password" placeholder="パスワード"></b-form-input>
                <b-form-input class="mb-3" type="password" placeholder="パスワードを再入力"></b-form-input>

                <b-form-checkbox size="sm" class="mb-3">
                    <b-link v-b-modal.modal-1 href="#">利用規約</b-link>
                    と
                    <b-link v-b-modal.modal-2 href="#">プライバシーポリシー</b-link>
                    を同意する
                </b-form-checkbox>

                <b-button block variant="warning" @click="isShowOtpInput = true">登録</b-button>
                <hr>
                <b-button block @click="showLogin">ログインに戻る</b-button>
            </div>
            <div v-if="isShowOtpInput">

                <b-form-group id="verify-code">
                    <label for="verify-code">認証コード</label>
                    <p style="font-size: 14px;" class="text-secondary">
                        futenji@gamil.com宛に認証コードを送信されました、下記に認証コードを入力してあなたのメールアドレスを認証してください。</p>
                    <SimpleOtpInput class="mb-3" />
                    <b-form-invalid-feedback :state="false" class="mb-3">
                        認証コードが間違っています。
                    </b-form-invalid-feedback>
                    <b-form-invalid-feedback :state="false" class="mb-3">
                        認証コードの有効期限が切れています。
                    </b-form-invalid-feedback>
                    <p style="font-size: 14px;" class="mb-0 text-secondary">コードの有効期限まであと 14:12 です。</p>
                </b-form-group>

                <b-link style="font-size: 14px;">認証コードを再送</b-link>
                <hr>
                <b-button block @click="isShowOtpInput = false">登録内容を変更</b-button>
            </div>
        </form>

        <b-modal id="modal-1" title="利用規約">
            <p class="my-4">利用規約</p>
        </b-modal>

        <b-modal id="modal-2" title="プライバシーポリシー">
            <p class="my-4">プライバシーポリシー</p>
        </b-modal>
    </div>
</template>

<script>
import SimpleOtpInput from "vue-simple-otp-input";
import "vue-simple-otp-input/dist/vue-simple-otp-input.css";

export default {
    components: {
        SimpleOtpInput,
    },
    methods: {
        showLogin() {
            this.$emit('showLogin')
        }
    },
    data() {
        return {
            isShowOtpInput: false,
        }
    }
}
</script>
