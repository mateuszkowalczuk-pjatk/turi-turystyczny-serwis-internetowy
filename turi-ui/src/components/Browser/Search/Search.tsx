import SearchDate from "./SearchDate";
import SearchInput from "./SearchInput";
import SearchMap from "./SearchMap";
import SearchButton from "./SearchButton";
import styles from './Search.module.css';

const Search = () => {
    return (
        <div className={styles.search}>
            <SearchDate />
            <SearchInput />
            <SearchMap />
            <SearchButton />
        </div>
    )
}

export default Search;