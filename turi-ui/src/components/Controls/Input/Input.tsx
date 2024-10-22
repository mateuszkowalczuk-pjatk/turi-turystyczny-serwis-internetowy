import './Input.module.css'

interface Props {
    placeholder: string;
}

export const Input = ({ placeholder }: Props) => {
    return (
        <input
            type="text"
            placeholder={placeholder}
        />
    )
}

export default Input