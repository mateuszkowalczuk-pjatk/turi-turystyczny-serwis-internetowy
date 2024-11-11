import styles from './Button.module.css'

interface Props {
    text: string
    onClick?: () => void
    className?: string
    type?: 'button' | 'submit' | 'reset'
    disabled?: boolean
}

export const GreyButton = ({ text, onClick, className, type, disabled }: Props) => {
    return (
        <button
            className={`${styles.button} ${styles.greyButton} ${className ? className : ''}`}
            onClick={onClick}
            type={type}
            disabled={disabled}
        >
            {text}
        </button>
    )
}

export const GreenButton = ({ text, onClick, type, disabled }: Props) => {
    return (
        <button
            className={`${styles.button} ${styles.greenButton}`}
            onClick={onClick}
            type={type}
            disabled={disabled}
        >
            {text}
        </button>
    )
}
