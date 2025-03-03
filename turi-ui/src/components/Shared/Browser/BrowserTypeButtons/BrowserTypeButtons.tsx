import { useHooks } from '../../../../hooks/shared/useHooks.ts'
import { GreyButton } from '../../Controls/Button'
import { SearchMode } from '../../../../types/offer.ts'
import styles from './BrowserTypeButtons.module.css'

interface Props {
    mode: string
    setMode: (value: ((prevState: string) => string) | string) => void
}

const BrowserTypeButtons = ({ mode, setMode }: Props) => {
    const { t } = useHooks()

    return (
        <div className={styles.buttons}>
            <GreyButton
                text={t('home.dashboard.all-button')}
                onClick={() => setMode(SearchMode.ALL)}
                className={mode === SearchMode.ALL ? styles.selected : ''}
            />
            <GreyButton
                text={t('home.dashboard.stay-button')}
                onClick={() => setMode(SearchMode.STAY)}
                className={mode === SearchMode.STAY ? styles.selected : ''}
            />
            <GreyButton
                text={t('home.dashboard.attraction-button')}
                onClick={() => setMode(SearchMode.ATTRACTION)}
                className={mode === SearchMode.ATTRACTION ? styles.selected : ''}
            />
        </div>
    )
}

export default BrowserTypeButtons
