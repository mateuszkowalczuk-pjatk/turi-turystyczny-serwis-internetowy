import styles from './SearchButton.module.css';

interface Props {
    text: string;
}

const SearchButton = ({ text }: Props) => {
    return (
        <div className={styles.search}>
            <button className={styles.button}>
                { text }
            </button>
        </div>
    )
}

export default SearchButton;