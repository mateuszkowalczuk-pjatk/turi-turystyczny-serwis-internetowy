import styles from './Button.module.css'

interface Props {
    text: string;
}

export const GreyButton = ({ text }: Props) => {
    return (
        <button className={`${styles.button} ${styles.greyButton}`}>
            { text }
        </button>
    )
}

export const GreenButton = ({ text }: Props) => {
    return (
        <button className={`${styles.button} ${styles.greenButton}`}>
            { text }
        </button>
    )
}