import Input from '../../../Controls/Input'
import './SignUpPersonalInput.module.css'

const SignUpPersonalInput = ({ text }: { text: string }) => {
    return (
        <Input
            placeholder={ text }
        />
    )
}

export default SignUpPersonalInput;