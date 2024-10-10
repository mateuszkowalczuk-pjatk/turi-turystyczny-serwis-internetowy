import styles from './Input.module.css';

interface Props {
    text: string;
}

export const Input = ({ text }: Props) => {
    return (
        <div className={styles.input}>
            <input
                type="text"
                placeholder={text}
            />
        </div>
    )
}

export default Input;