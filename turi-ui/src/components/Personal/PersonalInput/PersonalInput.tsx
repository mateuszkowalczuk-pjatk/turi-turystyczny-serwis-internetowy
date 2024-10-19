import Input from '../../Controls/Input'
import './PersonalInput.module.css'

const PersonalInput = ({ text }: { text: string }) => {
    return (
        <Input
            placeholder={ text }
        />
    )
}

export default PersonalInput