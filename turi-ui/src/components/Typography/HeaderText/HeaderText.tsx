import styles from './HeaderText.module.css';

interface Props {
    text: string;
}

const HeaderText = ({ text }: Props) => {
    return (
        <p className={styles.text}>
            { text }
        </p>
    )
}

export default HeaderText;